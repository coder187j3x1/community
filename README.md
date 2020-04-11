## Community -- build by java

### github 登录权限：
1. 引导用户跳转 github 授权登录页面 https://github.com/login/oauth/authorize，参数如下
    - client_id：必填，注册 OAuth App 时从 GitHub 得到的 client_id
    - redirect_uri：授权后将用户发送到应用程序中的URL，即 注册 OAuth 时填写的 Authorization callback URL
    - login：建议用于登录和授权应用程序的特定帐户
    - scope：授权范围
    - state：不可猜测的随机字符串。它用于防止跨站点请求伪造攻击
    - allow_signup：在OAuth流程期间，是否向未认证的用户提供注册GitHub的选项。默认值为true。false当策略禁止注册时使用
2. 用户授权后重定向到 redirect_uri，返回code 参数值，再通过code 再次访问 github：https://github.com/login/oauth/access_token
    - client_id：必填
    - client_secret：必填，注册 OAuth App 时从 GitHub 得到的 client_secret
    - code：必填，收到的作为对步骤1的响应的代码
    - redirect_uri
    - state
    
    默认情况下，github 响应形式为：
    ```
   access_token=e72e16c7e42f292c6912e7710c838347ae178b4a&token_type=bearer
   ```
   还可以根据 Accept Header 以不同的格式接收内容
   ```
   Accept: application/json
   {"access_token":"e72e16c7e42f292c6912e7710c838347ae178b4a", "scope":"repo,gist", "token_type":"bearer"}
   
   Accept: application/xml
   <OAuth>
     <token_type>bearer</token_type>
     <scope>repo,gist</scope>
     <access_token>e72e16c7e42f292c6912e7710c838347ae178b4a</access_token>
   </OAuth>
   ```
3. 通过获取的access_token 换取 用户信息: https://api.github.com/user，参数：
    - access_token：第二步得到的 access_token 令牌
    
### 项目逻辑
- 实体类
    - 数据库模型 model
    - 传输层模型 DTO(Data Transfer Object)
- DAO (Data Access Object)
- 控制层
- 服务层

### 分页显示
- thymeleaf
    - 前端 th:each
    - 后端 model 携带参数

### 资料
[Github OAuth](https://developer.github.com/apps/building-github-apps/)



***

待扩展：

- 问题评论的回复功能：
  - 详细说明：目前无法简便的显示出对谁的回复，比如，Ⅲ级评论显示出 userA @ userB ：XXX 之类的信息
    - Ⅰ级评论：对**问题**的**回答**；
    - Ⅱ级评论：对**问题的回答**的**回复或评论**；即对Ⅰ级评论的回复
    - Ⅲ级评论：对**问题回答的回复或评论**的**回复**；即对Ⅱ级评论或Ⅲ级评论的回复
  - 也可以分为两级评论，显示Ⅱ级评论的回复对象；此时Ⅱ级评论表示对Ⅰ级或Ⅱ级评论的回复（目前情况）
  - 解决方法：在每一个最后一级的评论标签下加一个回复按钮