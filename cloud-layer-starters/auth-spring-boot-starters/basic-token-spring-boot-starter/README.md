# basic-token-spring-boot-starter

### 乞丐版授权认证，基于拦截器实现，虽然是乞丐版，但代码可控度比较高，对于APP接口开发，足够了，足够小巧灵活。

    支持集群模式：是（灵活支持，默认支持Redis存储，支持容器存储，同时也支持用户自定义的实现）
    存储方式：暂时支持容器内部存储和Redis存储（分布式项目，或者服务器重启依然保证登录状态可用）
    支持类型：原生session、自定义token；
    权限粒度：Control级别
    依赖：无（用户默认无任何字段，完全可以自由实现）
    角色权限：支持（灵活可选）
    资源权限：支持 (灵活可选)