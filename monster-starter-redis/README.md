### RedisLock 参考
#### 添加配置
```yaml
kte:
  lock:
    enabled: true
    address: redis://{host}:6379
    password: 'xxxxx'
    database: 3
```
#### 一、直接调用
```
@Autowired
private RedisLockClient redisLockClient

/**
 * @param key         锁定的Key，一般为 业务类型前缀 + 锁定维度唯一值（订单ID、用户ID等）
 * @param lockType    锁类型 重入锁/公平锁
 * @param waitTime    获取锁等待时间   
 * @param leaseTime   自动解锁时间
 * @param unit        时间单位TimeUnit             
 **/
redisLockClient.lock(LOCK_KEY + orderId,  LockType.REENTRANT, 30, 100, TimeUnit.SECONDS,() -> {
    // 业务处理
}
```
#### 二、注解@RedisLock切面调用
注解参数说明：
> // redis key = value : param  
> value：业务类型  
> param：当前业务下的锁定唯一值  
> waitTime:加锁等待时间 默认30  
> leaseTime：自动解锁时间 默认100  
> timeUnit：时间单位 默认秒  
> // 新加字段，之前加锁等待超时调用方无感，这里true会在加锁等待超时返回异常  
> explicitFail：加锁等待超时是否显式失败 默认false  
> message：加锁等待超时异常信息
```
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/redis/{id}")
    @RedisLock(value = "aaaa", param = "#id", waitTime = 0, explicitFail = true, message = "请勿重复提交")
    public R<?> test(@PathVariable("id") Integer id) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return R.data(null);
    }
}
```
**注意事项**
1. RedisLockAspect 没Order属性，定义其他切面有需要锁住的逻辑需注意
2. 加锁等待超时会抛出非受检异常RedisLockException，可以自行捕获处理