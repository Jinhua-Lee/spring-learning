# 02_Spring事务传播行为小结

## 〇、约定

**事务传播行为**的理解就是多个事务方法相互调用时,事务如何在这些方法间传播。

> 举个栗子，**方法A**是一个事务的方法，方法A执行过程中**调用了方法B**，那么**方法B有无事务**以及方法B**对事务的要求不同**都会对方法A的事务具体执行造成影响，同时方法A的事务对方法B的事务执行也有影响，这种影响具体是什么就由两个方法所定义的**事务传播类型**所决定。

### 1. 业务层方法约定

##### 两个insert方法注解中的传播行为供更改，测试的即是上层事务对这两个事务的传播影响

```java
/**
 * 事务传播行为测试的业务类<p>&emsp;
 * 修改两个业务方法的事务注解，测试七种传播行为
 *
 * @author Jinhua
 * @version 1.0
 * @date 2021/6/7 20:49
 */
@Service
public class PropagationService {

    private PropagationMapper propagationMapper;

    @Autowired
    public void setPropagationMapper(PropagationMapper propagationMapper) {
        this.propagationMapper = propagationMapper;
    }

    @Transactional(propagation = Propagation.XXX, rollbackFor = Exception.class)
    public boolean addCommodities(List<Commodity> commodities) {
        if (CollectionUtils.isEmpty(commodities)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        int insert = propagationMapper.addCommodities(commodities);
        System.out.println("insert = " + insert);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.XXX, rollbackFor = Exception.class)
    public boolean addCustomers(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        int insert = propagationMapper.addCustomers(customers);
        System.out.println("insert = " + insert);
        return insert > 0;
    }

    @Transactional(propagation = Propagation.XXX, rollbackFor = Exception.class)
    public boolean addCustomersException(List<Customer> customers) {
        if (CollectionUtils.isEmpty(customers)) {
            throw new RuntimeException("待插入集合不能为空！");
        }
        int insert = propagationMapper.addCustomers(customers);
        System.out.println("insert = " + insert);
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        return insert > 0;
    }
}
```

### 2. 调用举例

```java
@RunWith(SpringJUnit4ClassRunner.class)
public class PropagationTest {
    
    protected PropagationService propagationService;

    @Autowired
    public void setPropagationService(PropagationService propagationService) {
        this.propagationService = propagationService;
    }
    
    @Test
    @Transactional
    public void testMain() {
        // 组织事务调用过程
    }
}
```

## 一、七种事务传播行为

### 0. 个人总结规律

1. 嵌套调用和扁平组合两种方式，即可构建一个类似于对象引用关系的事务调用关系。
2. 总是通过上层调用方来决定自身是否启用事务，启用哪个事务。
3. 自身仅表示准备好了一个原子过程，是否以原子方式执行。

### 1. Required（有担当）

#### 上层无事务，创建；上层有事务，加入。

```java
public class RequireTest extends PropagationTest {

    /**
     * 1.1 调用方法没有事务，两个事务方法各自独立
     */
    @Test
    public void testNoTx_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 该测试方法未开启事务，两个方法各自独立，商品成功，顾客失败！(测试成功)
    }

    /**
     * 1.2.1 调用方法存在事务，外围方法中有异常<p>&emsp;
     * <p>
     * 结果：<p>&emsp;
     * 开启事务，外围方法抛异常，商品和顾客都插入失败！
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxOut_Required_Required() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 同属外围事务方法，外围方法抛异常，商品和顾客都插入失败！（测试成功）
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 1.2.2 调用方法存在事务，其中一个子事务方法异常<p>&emsp;
     * <p>
     * 结果：<p>&emsp;
     * 开启事务，其中一个事务抛异常，则商品和顾客都插入失败！
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 开启事务，两个方法同属外围事务，商品和顾客均插入失败！(测试成功)
    }

    /**
     * 1.2.3 调用方法存在事务，就加入该事务<p>&emsp;
     * <p>
     * 结果：<p>&emsp;
     * 即使被catch不被外部感知，但发生回滚了。整个事务都回滚
     */
    @Test
    @Transactional
    public void testTxOut_Required_RequiredExTry() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        System.out.println("comRes = " + comRes);
        try {
            boolean cusRes = propagationService.addCustomersException(buildCustomers());
            System.out.println("cusRes = " + cusRes);
        } catch (Exception ignored) {
            // 即使被catch不被外部感知，但发生回滚，同属外围事务，会被事务管理器监测到。整个事务都回滚。（测试成功）
        }
    }
}
```

### 2. Supports（无担当）

#### 上层无事务，以非事务方式执行；上层有事务，加入。

```java
public class SupportsTest extends PropagationTest {

    /**
     * 2.1 调用方法未开启事务，以非事务方式执行
     */
    @Test
    public void testNoTx_Supports_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 该测试方法未开启事务，非事务执行，商品和顾客都插入成功！（测试成功）
    }

    /**
     * 2.2 调用方法开启Required事务，两个子事务(supports)方法加入该事务
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Supports_Required_RequiredEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 开启Required事务，第二个子事务方法抛异常，整个事务回滚，商品和顾客都插入失败！（测试成功）
    }
}
```

### 3. Mandatory（有想法，不实践）

#### 要求上层有事务，否则抛异常。

```java
public class MandatoryTest extends PropagationTest {

    /**
     * 3.1 调用方法不存在事务，就抛出异常，不执行
     */
    @Test
    public void testNoTx_Mandatory_Mandatory() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 调用方法未开启事务，不执行，两批数据都插入失败！（测试通过）
        // 直接抛出异常 org.springframework.transaction.IllegalTransactionStateException:
        // No existing transaction found for transaction marked with propagation 'mandatory'
        throw new RuntimeException("手动抛出 [运行时异常] ");
    }

    /**
     * 3.2 调用方法存在事务，就加入该事务；<p>&emsp;
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Mandatory_MandatoryEx() {
        // 该测试方法开启事务，加入事务
        // 第二个事务方法中有异常，但所有事务都加入外围事务，外围事务回滚，所有数据插入失败！(测试成功)
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
    }
}
```

### 4. Requires_New（激进，除旧布新，建立与上层无关的独立事务）

#### 始终开启新事务，与上层事务无关（父子事务独立）。

```java
public class RequiresNewTest extends PropagationTest {

    /**
     * 4.1 上层无事务，创建独立事务，事务间不受影响
     */
    @Test
    public void testNoTx_RequiresNew_RequiresNewEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 上层无事务，两个事务独立，则商品添加成功，顾客添加失败！（测试成功）
    }

    /**
     * 4.2 上层有事务，也创建独立事务。
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_RequiresNew_RequiresNewEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 上层有事务，两个事务独立，则商品添加成功，顾客添加失败！（测试成功）
    }

    /**
     * 4.3 上层有事务，外围异常抛出
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxTry_RequiresNew_RequiresNew() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 外围方法异常，但内层方法不属于外围方法的事务，所以商品和顾客仍然插入成功！（测试成功）
    }
}
```

### 5. Not_Supported（墨守成规）

#### 始终以非事务方式执行，不抛异常。

```java
public class NotSupportedTest extends PropagationTest {

    /**
     * 5.1 不存在，则以非事务方式执行操作
     */
    @Test
    public void testNoTx_NotSupported_NotSupportedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 非事务方式，商品插入成功，顾客插入失败！（测试成功）
    }

    /**
     * 5.2 当前存在事务，就把当前事务挂起（影响上层事务）
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_NotSupported_NotSupportedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 非事务方式，挂起上层事务，商品和顾客都插入成功！（测试成功）
    }
}
```

### 6. Never（画地为牢，抱残守缺）

#### 以非事务方式执行，有事务则抛异常。

```java
public class NeverTest extends PropagationTest {

    /**
     * 6.1 以非事务方式执行
     */
    @Test
    public void testNoTx_Never_Never() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        if (true) {
            throw new RuntimeException("手动抛出 [运行时异常] ");
        }
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 非事务方式，商品插入成功，顾客插入失败！（测试成功）
    }

    /**
     * 6.2 上层存在事务，则直接抛出异常
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Never_Never() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 上层存在事务，直接抛异常，方法不执行，商品和顾客都插入失败！（测试成功）
    }
}
```

### 7. Nested（吐故纳新，保留子事务对父事务的依赖影响）

#### 上层无事务，开启新事务（同Required）；上层有事务，以嵌套事务方式执行（父子事物有依赖，子事物回滚，父事物也回滚）。

```java
public class NestedTest extends PropagationTest {

    /**
     * 7.1 上层无事务，创建新事务
     */
    @Test
    public void testNoTx_Nested_NestedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 两个事务各自独立，顾客插入有异常抛出，则商品插入成功，顾客都插入失败！（测试成功）
    }

    /**
     * 7.2 上层有事务，保留事务依赖性
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Nested_NestedEx() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomersException(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        // 顾客事务发生异常回滚，上层事务也要回滚，则所有都插入失败！（测试成功）
    }

    /**
     * 7.3 上层有事务，对于异常的子事务捕获异常
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTx_Nested_NestedExTry() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        System.out.println("comRes = " + comRes);
        try {
            boolean cusRes = propagationService.addCustomersException(buildCustomers());
            System.out.println("cusRes = " + cusRes);
        } catch (Exception ignored) {
            // FIXME: 2021/6/9 顾客事务发生异常回滚，但异常不暴露给上层，上层事务执行成功，则商品插入成功，顾客插入失败！（测试失败！）
        }
    }

    /**
     * 7.4 上层有事务，外围事务方法异常
     */
    @Test
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void testTxEx_Nested_Nested() {
        boolean comRes = propagationService.addCommodities(buildCommodities());
        boolean cusRes = propagationService.addCustomers(buildCustomers());
        System.out.println("comRes = " + comRes);
        System.out.println("cusRes = " + cusRes);
        throw new RuntimeException("手动抛出 [运行时异常] ");
        // 嵌套事务，父事务异常，子事务的也插入失败！（测试成功）
    }
}
```

这里需要注意两点：

- 和**REQUIRES_NEW**的区别

> **REQUIRES_NEW**是新建一个事务并且新开启的这个事务**与原有事务无关**，而NESTED则是当前存在事务时（我们把当前事务称之为父事务）会开启一个嵌套事务（称之为一个子事务）。
> 在NESTED情况下父事务回滚时，子事务也会回滚，而在REQUIRES_NEW情况下，原有事务回滚，不会影响新开启的事务。

- 和**REQUIRED**的区别

> **REQUIRED**情况下，调用方存在事务时，则被调用方和调用方**使用同一事务**，那么被调用方出现异常时，由于共用一个事务，所以无论调用方是否catch其异常，事务都会回滚
> 而在**NESTED**情况下，被调用方发生异常时，调用方**可以catch其异常，这样只有子事务回滚**，父事务不受影响
