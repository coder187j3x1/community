package com.seaxll.community.service;

import com.seaxll.community.dto.NotificationDTO;
import com.seaxll.community.dto.PaginationDTO;
import com.seaxll.community.enums.ErrorCode;
import com.seaxll.community.enums.NotificationStatus;
import com.seaxll.community.enums.NotificationType;
import com.seaxll.community.exception.CommunityException;
import com.seaxll.community.mapper.NotificationMapper;
import com.seaxll.community.model.Notification;
import com.seaxll.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ClassName: NotificationService
 * Description:
 * date: 2020/4/12 8:26
 *
 * @author seaxll
 * @since JDK 1.8
 */
@Component
public class NotificationService {

    @Autowired
    private NotificationMapper notificationMapper;

    /**
     *
     * @param userId
     *      Integer
     * @param page
     *      Integer
     * @param size
     *      Integer
     * @return PaginationDTO
     */
    public PaginationDTO getNotificationList(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        int totalPage;
        // 计算总通知数量
        Integer totalCount = notificationMapper.count(userId);
        if (totalCount % size == 0) {
            totalPage = totalCount / size;
        } else {
            totalPage = totalCount / size + 1;
        }

        page = page < 1 ? 1 : (page > totalPage ? totalPage : page);

        paginationDTO.setPagination(totalPage, page);

        int offset = size * (page - 1);
        List<Notification> notifications = notificationMapper.findNotificationByUserId(userId, offset, size);

        if (notifications.size() == 0) {
            return paginationDTO;
        }

        List<NotificationDTO> notificationDTOS = new ArrayList<>();

        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification, notificationDTO);
            notificationDTO.setTypeName(NotificationType.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        paginationDTO.setNotifications(notificationDTOS);
        return paginationDTO;
    }

    public Integer unreadCount(Integer userId) {
        return notificationMapper.unreadCount(userId);
    }

    public NotificationDTO read(Integer id, User user) {
        Notification notification = notificationMapper.selectById(id);
        if (notification == null) {
            throw new CommunityException(ErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiverId(), user.getId())) {
            throw new CommunityException(ErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatus.READ.getStatus());
        notificationMapper.updateNotificationById(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationType.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
