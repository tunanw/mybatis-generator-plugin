# 这是 MyBatis Generator 插件的拓展插件包
应该说使用Mybatis就一定离不开[MyBatis Generator](https://github.com/mybatis/generator)这款代码生成插件，而这款插件自身还提供了插件拓展功能用于强化插件本身，官方已经提供了一些[拓展插件](http://www.mybatis.org/generator/reference/plugins.html)，本项目的目的也是通过该插件机制来强化Mybatis Generator本身，方便和减少我们平时的代码开发量。  
>因为插件是本人兴之所至所临时发布的项目（本人已近三年未做JAVA开发，代码水平请大家见谅），但基本插件都是在实际项目中经过检验的请大家放心使用，但因为项目目前主要数据库为MySQL，Mybatis实现使用Mapper.xml方式，所以代码生成时对于其他数据库和注解方式的支持未予考虑，请大家见谅。  
  
---------------------------------------
插件列表：  
* [查询单条数据插件（SelectOneByExamplePlugin）](#1-查询单条数据插件)
* [MySQL分页插件（LimitPlugin）](#2-mysql分页插件)
* [数据Model链式构建插件（ModelBuilderPlugin）](#3-数据model链式构建插件)
* [Example Criteria 增强插件（ExampleEnhancedPlugin）](#4-example-增强插件exampleandiforderby)
* [Example 目标包修改插件（ExampleTargetPlugin）](#5-example-目标包修改插件)
* [批量插入插件（BatchInsertPlugin）](#6-批量插入插件)
* [逻辑删除插件（LogicalDeletePlugin）](#7-逻辑删除插件)
* [数据Model属性对应Column获取插件（ModelColumnPlugin）](#8-数据model属性对应column获取插件)
* [存在即更新插件（UpsertPlugin）](#9-存在即更新插件)
* [Selective选择插入更新增强插件（SelectiveEnhancedPlugin）](#10-selective选择插入更新增强插件)
* [Table增加前缀插件（TablePrefixPlugin）](#11-table增加前缀插件)
* [Table重命名插件（TableRenamePlugin）](#12-table重命名插件)
* [自定义注释插件（CommentPlugin）](#13-自定义注释插件)
* [增量插件（IncrementsPlugin）](#14-增量插件)
* [查询结果选择性返回插件（SelectSelectivePlugin）](#15-查询结果选择性返回插件)
* [~~官方ConstructorBased配置BUG临时修正插件（ConstructorBasedBugFixPlugin）~~](#16-官方constructorbased配置bug临时修正插件)

---------------------------------------
Maven引用：  
```xml
<dependency>
  <groupId>com.itfsw</groupId>
  <artifactId>mybatis-generator-plugin</artifactId>
  <version>1.1.0</version>
</dependency>
```
---------------------------------------
MyBatis Generator 参考配置（插件依赖应该配置在mybatis-generator-maven-plugin插件依赖中[[issues#6]](https://github.com/itfsw/mybatis-generator-plugin/issues/6)）
```xml
<!-- mybatis-generator 自动代码插件 -->
<plugin>
    <groupId>org.mybatis.generator</groupId>
    <artifactId>mybatis-generator-maven-plugin</artifactId>
    <version>1.3.6</version>
    <configuration>
        <!-- 配置文件 -->
        <configurationFile>src/main/resources/mybatis-generator.xml</configurationFile>
        <!-- 允许移动和修改 -->
        <verbose>true</verbose>
        <overwrite>true</overwrite>
    </configuration>
    <dependencies>
        <!-- jdbc 依赖 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.driver.version}</version>
        </dependency>
        <dependency>
            <groupId>com.itfsw</groupId>
            <artifactId>mybatis-generator-plugin</artifactId>
            <version>${mybatis.generator.plugin.version}</version>
        </dependency>
    </dependencies>
</plugin>
```
---------------------------------------
### 1. 查询单条数据插件
对应表Mapper接口增加了方法  
插件：
```xml
<!-- 查询单条数据插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.SelectOneByExamplePlugin"/>
```
使用：  
```java
public interface TbMapper {    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Tb selectOneByExample(TbExample example);
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    // Model WithBLOBs 时才有
    TbWithBLOBs selectOneByExampleWithBLOBs(TbExample example);
}
```
### 2. MySQL分页插件
对应表Example类增加了Mysql分页方法，limit(Integer rows)、limit(Integer offset, Integer rows)和page(Integer page, Integer pageSize)  
插件：
```xml
<!-- MySQL分页插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.LimitPlugin"/>
```
使用：  
```java
public class TbExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    protected Integer offset;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    protected Integer rows;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public TbExample limit(Integer rows) {
        this.rows = rows;
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public TbExample limit(Integer offset, Integer rows) {
        this.offset = offset;
        this.rows = rows;
        return this;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public TbExample page(Integer page, Integer pageSize) {
        this.offset = page * pageSize;
        this.rows = pageSize;
        return this;
    }
    
    // offset 和 rows 的getter&setter
    
    // 修正了clear方法
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table tb
     *
     * @mbg.generated
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
        rows = null;
        offset = null;
    }
}
public class Test {
    public static void main(String[] args) {
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1GreaterThan(1)
                .example()
                .limit(10)  // 查询前10条
                .limit(10, 10)  // 查询10~20条
                .page(1, 10)    // 查询第2页数据（每页10条）
        );
    }
}
```
### 3. 数据Model链式构建插件
这个是仿jquery的链式调用强化了表的Model的赋值操作  
插件：
```xml
<!-- 数据Model链式构建插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.ModelBuilderPlugin"/>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 直接new表Model的内部Builder类，赋值后调用build()方法返回对象
        Tb table = new Tb.Builder()
               .field1("xx")
               .field2("xx")
               .field3("xx")
               .field4("xx")
               .build();
        // 或者使用builder静态方法创建Builder
        Tb table = Tb.builder()
               .field1("xx")
               .field2("xx")
               .field3("xx")
               .field4("xx")
               .build();
    }
}
```
### 4. Example 增强插件(example,andIf,orderBy)
* Criteria的快速返回example()方法。  
* Criteria链式调用增强，以前如果有按条件增加的查询语句会打乱链式查询构建，现在有了andIf(boolean ifAdd, CriteriaAdd add)方法可一直使用链式调用下去。
* Example增强了setOrderByClause方法，新增orderBy(String orderByClause)方法直接返回example，增强链式调用，可以一路.下去了。
* 继续增强orderBy(String orderByClause)方法，增加orderBy(String ... orderByClauses)方法，配合数据Model属性对应Column获取插件（ModelColumnPlugin）使用效果更佳。   
插件：
```xml
<!-- Example Criteria 增强插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.ExampleEnhancedPlugin"/>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // -----------------------------------example-----------------------------------
        // 表Example.Criteria增加了工厂方法example()支持，使用后可链式构建查询条件使用example()返回Example对象
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1EqualTo(1)
                .andField2EqualTo("xxx")
                .example()
        );
        
        // -----------------------------------andIf-----------------------------------
        // Criteria增强了链式调用，现在一些按条件增加的查询条件不会打乱链式调用了
        // old
        TbExample oldEx = new TbExample();
        TbExample.Criteria criteria = oldEx
                .createCriteria()
                .andField1EqualTo(1)
                .andField2EqualTo("xxx");
        // 如果随机数大于0.5，附加Field3查询条件
        if (Math.random() > 0.5){
            criteria.andField3EqualTo(2)
                    .andField4EqualTo(new Date());
        }
        this.tbMapper.selectByExample(oldEx);

        // new
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1EqualTo(1)
                .andField2EqualTo("xxx")
                // 如果随机数大于0.5，附加Field3查询条件
                .andIf(Math.random() > 0.5, new TbExample.Criteria.ICriteriaAdd() {
                    @Override
                    public TbExample.Criteria add(TbExample.Criteria add) {
                        return add.andField3EqualTo(2)
                                .andField4EqualTo(new Date());
                    }
                })
                // 当然最简洁的写法是采用java8的Lambda表达式，当然你的项目是Java8+
                .andIf(Math.random() > 0.5, add -> add
                        .andField3EqualTo(2)
                        .andField4EqualTo(new Date())
                )
                .example()
        );
        
        // -----------------------------------orderBy-----------------------------------
        // old
        TbExample ex = new TbExample();
        ex.createCriteria().andField1GreaterThan(1);
        ex.setOrderByClause("field1 DESC");
        this.tbMapper.selectByExample(ex);

        // new
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1GreaterThan(1)
                .example()
                .orderBy("field1 DESC")
                // 这个配合数据Model属性对应Column获取插件（ModelColumnPlugin）使用
                .orderBy(Tb.Column.field1.asc(), Tb.Column.field3.desc())
        );
    }
}
```
### 5. Example 目标包修改插件
Mybatis Generator 插件默认把Model类和Example类都生成到一个包下，这样该包下类就会很多不方便区分，该插件目的就是把Example类独立到一个新包下，方便查看。  
插件：
```xml
<!-- Example 目标包修改插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.ExampleTargetPlugin">
    <!-- 修改Example类生成到目标包下 -->
    <property name="targetPackage" value="com.itfsw.mybatis.generator.dao.example"/>
</plugin>
```
### 6. 批量插入插件
提供了批量插入batchInsert和batchInsertSelective方法，因为方法使用了使用了JDBC的getGenereatedKeys方法返回插入主键，所以只能在MYSQL和SQLServer下使用。
需配合数据Model属性对应Column获取插件（ModelColumnPlugin）插件使用，实现类似于insertSelective插入列！  
PS:为了和Mybatis官方代码风格保持一致，1.0.5+版本把批量插入方法分开了，基于老版本1.0.5-版本生成的代码请继续使用“com.itfsw.mybatis.generator.plugins.BatchInsertOldPlugin”不影响使用  
PS:继续PS，本来想继续保留老版本代码，不影响老版本已经生成代码使用的，但是始终没有绕过BindingException，只能把代码移入BatchInsertOldPlugin类~~~~~  
插件：
```xml
<!-- 批量插入插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.BatchInsertPlugin"/>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 构建插入数据
        List<Tb> list = new ArrayList<>();
        list.add(
                new Tb.Builder()
                .field1(0)
                .field2("xx0")
                .field3(0)
                .createTime(new Date())
                .build()
        );
        list.add(
                new Tb.Builder()
                .field1(1)
                .field2("xx1")
                .field3(1)
                .createTime(new Date())
                .build()
        );
        // 普通插入，插入所有列
        this.tbMapper.batchInsert(list);
        // !!!下面按需插入指定列（类似于insertSelective），需要数据Model属性对应Column获取插件（ModelColumnPlugin）插件
        this.tbMapper.batchInsertSelective(list, Tb.Column.field1, Tb.Column.field2, Tb.Column.field3, Tb.Column.createTime);
    }
}
```
### 7. 逻辑删除插件
因为很多实际项目数据都不允许物理删除，多采用逻辑删除，所以单独为逻辑删除做了一个插件，方便使用。  
- 增加logicalDeleteByExample和logicalDeleteByPrimaryKey方法；
- 增加selectByPrimaryKeyWithLogicalDelete方法（[[pull#12]](https://github.com/itfsw/mybatis-generator-plugin/pull/12)）；
- 查询构造工具中增加逻辑删除条件andLogicalDeleted(boolean)；
- 数据Model增加逻辑删除条件andLogicalDeleted(boolean)；
- 增加逻辑删除常量IS_DELETED（已删除 默认值）、NOT_DELETED（未删除 默认值）（[[issues#11]](https://github.com/itfsw/mybatis-generator-plugin/issues/11)）；
 
插件：
```xml
<xml>
    <!-- 逻辑删除插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.LogicalDeletePlugin">
        <!-- 这里配置的是全局逻辑删除列和逻辑删除值，当然在table中配置的值会覆盖该全局配置 -->
        <!-- 逻辑删除列类型只能为数字、字符串或者布尔类型 -->
        <property name="logicalDeleteColumn" value="del_flag"/>
        <!-- 逻辑删除-已删除值 -->
        <property name="logicalDeleteValue" value="9"/>
        <!-- 逻辑删除-未删除值 -->
        <property name="logicalUnDeleteValue" value="0"/>
        <!-- 逻辑删除常量名称，不配置默认为 IS_DELETED -->
        <property name="logicalDeleteValue" value="DEL"/>
        <!-- 逻辑删除常量（未删除）名称，不配置默认为 NOT_DELETED -->
        <property name="logicalUnDeleteValue" value="UN_DEL"/>
    </plugin>
    
    <table tableName="tb">
        <!-- 这里可以单独表配置逻辑删除列和删除值，覆盖全局配置 -->
        <property name="logicalDeleteColumn" value="del_flag"/>
        <property name="logicalDeleteValue" value="1"/>
        <property name="logicalUnDeleteValue" value="-1"/>
    </table>
</xml>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 1. 逻辑删除ByExample
        this.tbMapper.logicalDeleteByExample(
                new TbExample()
                .createCriteria()
                .andField1EqualTo(1)
                .example()
        );

        // 2. 逻辑删除ByPrimaryKey
        this.tbMapper.logicalDeleteByPrimaryKey(1L);

        // 3. 同时Example中提供了一个快捷方法来过滤逻辑删除数据
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1EqualTo(1)
                // 新增了一个andDeleted方法过滤逻辑删除数据
                .andLogicalDeleted(true)
                // 当然也可直接使用逻辑删除列的查询方法，我们数据Model中定义了一个逻辑删除常量DEL_FLAG
                .andDelFlagEqualTo(Tb.IS_DELETED)
                .example()
        );
        
        // 4. 逻辑删除和未删除常量
        Tb tb = new Tb.Builder()
                .delFlag(Tb.IS_DELETED)   // 删除
                .delFlag(Tb.NOT_DELETED)    // 未删除
                .build()
                .andLogicalDeleted(true);   // 也可以在这里使用true|false设置逻辑删除

        // 5. selectByPrimaryKeyWithLogicalDelete V1.0.18 版本增加
        // 因为之前觉得既然拿到了主键这种查询没有必要，但是实际使用中可能存在根据主键判断是否逻辑删除的情况，这种场景还是有用的
        this.tbMapper.selectByPrimaryKeyWithLogicalDelete(1, true);
    }
}
```
### 8. 数据Model属性对应Column获取插件
项目中我们有时需要获取数据Model对应数据库字段的名称，一般直接根据数据Model的属性就可以猜出数据库对应column的名字，可是有的时候当column使用了columnOverride或者columnRenamingRule时就需要去看数据库设计了，所以提供了这个插件获取model对应的数据库Column。  
* 配合Example Criteria 增强插件（ExampleEnhancedPlugin）使用，这个插件还提供了asc()和desc()方法配合Example的orderBy方法效果更佳。
* 配合批量插入插件（BatchInsertPlugin）使用，batchInsertSelective(@Param("list") List<XXX> list, @Param("selective") XXX.Column ... insertColumns)。  

插件：
```xml
<!-- 数据Model属性对应Column获取插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.ModelColumnPlugin"/>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 1. 获取Model对应column
        String column = Tb.Column.createTime.value();

        // 2. 配合Example Criteria 增强插件（ExampleEnhancedPlugin）使用orderBy方法
        // old
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1GreaterThan(1)
                .example()
                .orderBy("field1 DESC, field3 ASC")
        );

        // better
        this.tbMapper.selectByExample(
                new TbExample()
                .createCriteria()
                .andField1GreaterThan(1)
                .example()
                .orderBy(Tb.Column.field1.desc(), Tb.Column.field3.asc())
        );
        
        // 3. 配合批量插入插件（BatchInsertPlugin）使用实现按需插入指定列
        List<Tb> list = new ArrayList<>();
        list.add(
                new Tb.Builder()
                .field1(0)
                .field2("xx0")
                .field3(0)
                .field4(new Date())
                .build()
        );
        list.add(
                new Tb.Builder()
                .field1(1)
                .field2("xx1")
                .field3(1)
                .field4(new Date())
                .build()
        );
        // 这个会插入表所有列
        this.tbMapper.batchInsert(list);
        // 下面按需插入指定列（类似于insertSelective）
        this.tbMapper.batchInsertSelective(list, Tb.Column.field1, Tb.Column.field2, Tb.Column.field3, Tb.Column.createTime);
    }
}
```
### 9. 存在即更新插件
1. 使用MySQL的[“insert ... on duplicate key update”](https://dev.mysql.com/doc/refman/5.7/en/insert-on-duplicate.html)实现存在即更新操作，简化数据入库操作（[[issues#2]](https://github.com/itfsw/mybatis-generator-plugin/issues/2)）。  
2. 在开启allowMultiQueries=true（默认不会开启）情况下支持upsertByExample，upsertByExampleSelective操作，但强力建议不要使用（需保证团队没有使用statement提交sql,否则会存在sql注入风险）（[[issues#2]](https://github.com/itfsw/mybatis-generator-plugin/issues/2)）。

插件：
```xml
<!-- 存在即更新插件 -->
<plugin type="com.itfsw.mybatis.generator.plugins.UpsertPlugin">
    <!-- 
    支持upsertByExample，upsertByExampleSelective操作
    ！需开启allowMultiQueries=true多条sql提交操作，所以不建议使用！
    -->
    <property name="allowMultiQueries" value="true"/>
</plugin>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 1. 未入库数据入库，执行insert
        Tb tb = new Tb.Builder()
                .field1(1)
                .field2("xx0")
                .delFlag(Tb.DEL_FLAG_ON)
                .build();
        int k0 = this.tbMapper.upsert(tb);
        // 2. 已入库数据再次入库,执行update（！！需要注意如触发update其返回的受影响行数为2）
        tb.setField2("xx1");
        int k1 = this.tbMapper.upsert(tb);

        // 3. 类似insertSelective实现选择入库
        Tb tb1 = new Tb.Builder()
                .field1(1)
                .field2("xx0")
                .build();
        int k2 = this.tbMapper.upsertSelective(tb1);
        tb1.setField2("xx1");
        int k3 = this.tbMapper.upsertSelective(tb1);

        // 4. 开启allowMultiQueries后增加upsertByExample，upsertByExampleSelective但强力建议不要使用（需保证团队没有使用statement提交sql,否则会存在sql注入风险）
        Tb tb2 = new Tb.Builder()
                .field1(1)
                .field2("xx0")
                .field3(1003)
                .delFlag(Tb.DEL_FLAG_ON)
                .build();
        int k4 = this.tbMapper.upsertByExample(tb2,
                new TbExample()
                        .createCriteria()
                        .andField3EqualTo(1003)
                        .example()
        );
        tb2.setField2("xx1");
        // !!! upsertByExample,upsertByExampleSelective触发更新时，更新条数返回是有问题的，这里只会返回0
        // 这是mybatis自身问题，也是不怎么建议开启allowMultiQueries功能原因之一
        int k5 = this.tbMapper.upsertByExample(tb2,
                new TbExample()
                        .createCriteria()
                        .andField3EqualTo(1003)
                        .example()
        );
        // upsertByExampleSelective 用法类似
        
        // 当Model WithBLOBs 存在时上述方法增加对应的 WithBLOBs 方法，举例如下：
        TbWithBLOBs tb3 = new Tb.Builder()
                .field1(1)
                .field2("xx0")
                .delFlag(Tb.DEL_FLAG_ON)
                .build();
        int k6 = this.tbMapper.upsertWithBLOBs(tb);
    }
}
```
### 10. Selective选择插入更新增强插件
项目中往往需要指定某些字段进行插入或者更新，或者把某些字段进行设置null处理，这种情况下原生xxxSelective方法往往不能达到需求，因为它的判断条件是对象字段是否为null，这种情况下可使用该插件对xxxSelective方法进行增强。  
>warning:配置插件时请把插件配置在所有插件末尾最后执行，这样才能把上面提供的某些插件的Selective方法也同时增强！

插件：
```xml
<!-- Selective选择插入更新增强插件！请配在所有插件末尾以便最后执行 -->
<plugin type="com.itfsw.mybatis.generator.plugins.SelectiveEnhancedPlugin"/>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 1. 指定插入或更新字段
        Tb tb = new Tb.Builder()
                .field1(1)
                .field2("xx2")
                .field3(1)
                .field4(new Date())
                .createTime(new Date())
                .delFlag(Tb.DEL_FLAG_ON)
                .build();
        // 只插入或者更新field1,field2字段
        this.tbMapper.insertSelective(tb.selective(Tb.Column.field1, Tb.Column.field2));
        this.tbMapper.updateByExampleSelective(tb.selective(Tb.Column.field1, Tb.Column.field2),
                new TbExample()
                        .createCriteria()
                        .andIdEqualTo(1l)
                        .example()
        );
        this.tbMapper.updateByPrimaryKeySelective(tb.selective(Tb.Column.field1, Tb.Column.field2));
        this.tbMapper.upsertSelective(tb.selective(Tb.Column.field1, Tb.Column.field2));
        this.tbMapper.upsertByExampleSelective(tb.selective(Tb.Column.field1, Tb.Column.field2),
                new TbExample()
                        .createCriteria()
                        .andField3EqualTo(1)
                        .example()
        );

        // 2. 更新某些字段为null
        this.tbMapper.updateByPrimaryKeySelective(
                new Tb.Builder()
                .id(1l)
                .field1(null)   // 方便展示，不用设也可以
                .build()
                .selective(Tb.Column.field1)
        );
    }
}
```
### 11. Table增加前缀插件
项目中有时会遇到配置多数据源对应多业务的情况，这种情况下可能会出现不同数据源出现重复表名，造成异常冲突。
该插件允许为表增加前缀，改变最终生成的Model、Mapper、Example类名以及xml名。  
>warning: 使用[Table重命名插件](12-table重命名插件)可以实现相同功能！  

插件：
```xml
<xml>
    <!-- Table增加前缀插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.TablePrefixPlugin">
        <!-- 这里配置的是全局表前缀，当然在table中配置的值会覆盖该全局配置 -->
        <property name="prefix" value="Cm"/>
    </plugin>
    
    <table tableName="tb">
        <!-- 这里可以单独表配置前缀，覆盖全局配置 -->
        <property name="suffix" value="Db1"/>
    </table>
</xml>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // Tb 表对应的Model、Mapper、Example类都增加了Db1的前缀
        // Model类名: Tb -> Db1Tb
        // Mapper类名: TbMapper -> Db1TbMapper
        // Example类名: TbExample -> Db1TbExample
        // xml文件名: TbMapper.xml -> Db1TbMapper.xml
    }
}
```
### 12. Table重命名插件
插件由来：  
记得才开始工作时某个隔壁项目组的坑爹项目，由于某些特定的原因数据库设计表名为t1~tn,字段名为f1~fn。
这种情况下如何利用Mybatis Generator生成可读的代码呢，字段可以用columnOverride来替换，Model、Mapper等则需要使用domainObjectName+mapperName来实现方便辨识的代码。  
>该插件解决：domainObjectName+mapperName?好吧我想简化一下，所以直接使用tableOverride（仿照columnOverride）来实现便于配置的理解。 

某些DBA喜欢在数据库设计时使用t_、f_这种类似的设计（[[issues#4]](https://github.com/itfsw/mybatis-generator-plugin/issues/4)），
这种情况下我们就希望能有类似[columnRenamingRule](http://www.mybatis.org/generator/configreference/columnRenamingRule.html)这种重命名插件来修正最终生成的Model、Mapper等命名。
>该插件解决：使用正则替换table生成的Model、Example、Mapper等命名。
 
项目中有时会遇到配置多数据源对应多业务的情况，这种情况下可能会出现不同数据源出现重复表名，造成异常冲突。
该插件可以实现和[Table增加前缀插件](11-table增加前缀插件)相同的功能，仿照如下配置。  
```xml
<property name="searchString" value="^"/>
<property name="replaceString" value="DB1"/>
```

插件：
```xml
<xml>
    <!-- Table重命名插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.TableRenamePlugin">
        <!-- 可根据具体需求确定是否配置 -->
        <property name="searchString" value="^T"/>
        <property name="replaceString" value=""/>
    </plugin>
    
    <table tableName="tb">
        <!-- 这个优先级最高，会忽略searchString、replaceString的配置 -->
        <property name="tableOverride" value="TestDb"/>
    </table>
</xml>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 1. 使用searchString和replaceString时，和Mybatis Generator一样使用的是java.util.regex.Matcher.replaceAll去进行正则替换
        // 表： T_USER
        // Model类名: TUser -> User
        // Mapper类名: TUserMapper -> UserMapper
        // Example类名: TUserExample -> UserExample
        // xml文件名: TUserMapper.xml -> UserMapper.xml
        
        // 2. 使用表tableOverride时，该配置优先级最高
        // 表： T_BOOK
        // Model类名: TBook -> TestDb
        // Mapper类名: TBookMapper -> TestDbMapper
        // Example类名: TBookExample -> TestDbExample
        // xml文件名: TBookMapper.xml -> TestDbMapper.xml
    }
}
```
### 13. 自定义注释插件
Mybatis Generator是原生支持自定义注释的（commentGenerator配置type属性），但使用比较麻烦需要自己实现CommentGenerator接口并打包配置赖等等。  
该插件借助freemarker极佳的灵活性实现了自定义注释的快速配置。  
>warning: 下方提供了一个参考模板，需要注意${mgb}的输出，因为Mybatis Generator就是通过该字符串判断是否为自身生成代码进行覆盖重写。  

>warning: 请注意拷贝参考模板注释前方空格，idea等工具拷贝进去后自动格式化会造成格式错乱。 

>warning: 模板引擎采用的是freemarker所以一些freemarker指令参数（如：<#if xx></#if>、${.now?string("yyyy-MM-dd HH:mm:ss")}）都是可以使用的，请自己尝试。

| 注释ID | 传入参数 | 备注 |
| ----- | ----- | ---- |
| addJavaFileComment | mgb<br>compilationUnit | Java文件注释   |
| addComment | mgb<br>xmlElement | Xml节点注释  |
| addRootComment | mgb<br>rootElement | Xml根节点注释  |
| addFieldComment | mgb<br>field<br>introspectedTable<br>introspectedColumn | Java 字段注释(非生成Model对应表字段时，introspectedColumn可能不存在)  |
| addModelClassComment | mgb<br>topLevelClass<br>introspectedTable | 表Model类注释  |
| addClassComment | mgb<br>innerClass<br>introspectedTable | 类注释  |
| addEnumComment | mgb<br>innerEnum<br>introspectedTable | 枚举注释  |
| addInterfaceComment | mgb<br>innerInterface<br>introspectedTable | 接口注释(itfsw插件新增类型)  |
| addGetterComment | mgb<br>method<br>introspectedTable<br>introspectedColumn | getter方法注释(introspectedColumn可能不存在)  |
| addSetterComment | mgb<br>method<br>introspectedTable<br>introspectedColumn | getter方法注释(introspectedColumn可能不存在)  |
| addGeneralMethodComment | mgb<br>method<br>introspectedTable | 方法注释  |

插件：
```xml
<xml>
    <!-- 自定义注释插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.CommentPlugin">
        <!-- 自定义模板路径 -->
        <property name="template" value="src/main/resources/mybatis-generator-comment.ftl" />
    </plugin>
</xml>
```
使用（参考模板）：  
```xml
<?xml version="1.0" encoding="UTF-8"?>
<template>
    <!-- #############################################################################################################
    /**
     * This method is called to add a file level comment to a generated java file. This method could be used to add a
     * general file comment (such as a copyright notice). However, note that the Java file merge function in Eclipse
     * does not deal with this comment. If you run the generator repeatedly, you will only retain the comment from the
     * initial run.
     * <p>
     *
     * The default implementation does nothing.
     *
     * @param compilationUnit
     *            the compilation unit
     */
    -->
    <comment ID="addJavaFileComment"></comment>

    <!-- #############################################################################################################
    /**
     * This method should add a suitable comment as a child element of the specified xmlElement to warn users that the
     * element was generated and is subject to regeneration.
     *
     * @param xmlElement
     *            the xml element
     */
    -->
    <comment ID="addComment"><![CDATA[
<!--
  WARNING - ${mgb}
  This element is automatically generated by MyBatis Generator, do not modify.
  @project https://github.com/itfsw/mybatis-generator-plugin
-->
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * This method is called to add a comment as the first child of the root element. This method could be used to add a
     * general file comment (such as a copyright notice). However, note that the XML file merge function does not deal
     * with this comment. If you run the generator repeatedly, you will only retain the comment from the initial run.
     * <p>
     *
     * The default implementation does nothing.
     *
     * @param rootElement
     *            the root element
     */
    -->
    <comment ID="addRootComment"></comment>

    <!-- #############################################################################################################
    /**
     * This method should add a Javadoc comment to the specified field. The field is related to the specified table and
     * is used to hold the value of the specified column.
     * <p>
     *
     * <b>Important:</b> This method should add a the nonstandard JavaDoc tag "@mbg.generated" to the comment. Without
     * this tag, the Eclipse based Java merge feature will fail.
     *
     * @param field
     *            the field
     * @param introspectedTable
     *            the introspected table
     * @param introspectedColumn
     *            the introspected column
     */
    -->
    <comment ID="addFieldComment"><![CDATA[
<#if introspectedColumn??>
/**
    <#if introspectedColumn.remarks?? && introspectedColumn.remarks != ''>
 * Database Column Remarks:
        <#list introspectedColumn.remarks?split("\n") as remark>
 *   ${remark}
        </#list>
    </#if>
 *
 * This field was generated by MyBatis Generator.
 * This field corresponds to the database column ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
<#else>
/**
 * This field was generated by MyBatis Generator.
 * This field corresponds to the database table ${introspectedTable.fullyQualifiedTable}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
</#if>
    ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds a comment for a model class.  The Java code merger should
     * be notified not to delete the entire class in case any manual
     * changes have been made.  So this method will always use the
     * "do not delete" annotation.
     *
     * Because of difficulties with the Java file merger, the default implementation
     * of this method should NOT add comments.  Comments should only be added if
     * specifically requested by the user (for example, by enabling table remark comments).
     *
     * @param topLevelClass
     *            the top level class
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addModelClassComment"><![CDATA[
/**
<#if introspectedTable.remarks?? && introspectedTable.remarks != ''>
 * Database Table Remarks:
<#list introspectedTable.remarks?split("\n") as remark>
 *   ${remark}
</#list>
</#if>
 *
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table ${introspectedTable.fullyQualifiedTable}
 *
 * ${mgb} do_not_delete_during_merge
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds the inner class comment.
     *
     * @param innerClass
     *            the inner class
     * @param introspectedTable
     *            the introspected table
     * @param markAsDoNotDelete
     *            the mark as do not delete
     */
    -->
    <comment ID="addClassComment"><![CDATA[
/**
 * This class was generated by MyBatis Generator.
 * This class corresponds to the database table ${introspectedTable.fullyQualifiedTable}
 *
 * ${mgb}<#if markAsDoNotDelete?? && markAsDoNotDelete> do_not_delete_during_merge</#if>
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds the enum comment.
     *
     * @param innerEnum
     *            the inner enum
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addEnumComment"><![CDATA[
/**
 * This enum was generated by MyBatis Generator.
 * This enum corresponds to the database table ${introspectedTable.fullyQualifiedTable}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds the interface comment.
     *
     * @param innerInterface
     *            the inner interface
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addInterfaceComment"><![CDATA[
/**
 * This interface was generated by MyBatis Generator.
 * This interface corresponds to the database table ${introspectedTable.fullyQualifiedTable}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds the getter comment.
     *
     * @param method
     *            the method
     * @param introspectedTable
     *            the introspected table
     * @param introspectedColumn
     *            the introspected column
     */
    -->
    <comment ID="addGetterComment"><![CDATA[
<#if introspectedColumn??>
/**
 * This method was generated by MyBatis Generator.
 * This method returns the value of the database column ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 *
 * @return the value of ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
<#else>
/**
 * This method was generated by MyBatis Generator.
 * This method returns the value of the field ${method.name?replace("get","")?replace("is", "")?uncap_first}
 *
 * @return the value of field ${method.name?replace("get","")?replace("is", "")?uncap_first}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
</#if>
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds the setter comment.
     *
     * @param method
     *            the method
     * @param introspectedTable
     *            the introspected table
     * @param introspectedColumn
     *            the introspected column
     */
    -->
    <comment ID="addSetterComment"><![CDATA[
<#if introspectedColumn??>
/**
 * This method was generated by MyBatis Generator.
 * This method sets the value of the database column ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 *
 * @param ${method.parameters[0].name} the value for ${introspectedTable.fullyQualifiedTable}.${introspectedColumn.actualColumnName}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
<#else>
/**
 * This method was generated by MyBatis Generator.
 * This method sets the value of the field ${method.name?replace("set","")?uncap_first}
 *
 * @param ${method.parameters[0].name} the value for field ${method.name?replace("set","")?uncap_first}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
</#if>
        ]]></comment>

    <!-- #############################################################################################################
    /**
     * Adds the general method comment.
     *
     * @param method
     *            the method
     * @param introspectedTable
     *            the introspected table
     */
    -->
    <comment ID="addGeneralMethodComment"><![CDATA[
/**
 * This method was generated by MyBatis Generator.
 * This method corresponds to the database table ${introspectedTable.fullyQualifiedTable}
 *
 * ${mgb}
 * @project https://github.com/itfsw/mybatis-generator-plugin
 */
        ]]></comment>
</template>
```
### 14. 增量插件
为更新操作生成set filedxxx = filedxxx +/- inc 操作，方便某些统计字段的更新操作，常用于某些需要计数的场景；  

插件：
```xml
<xml>
    <!-- 增量插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.IncrementsPlugin" />
    
    <table tableName="tb">
        <!-- 配置需要进行增量操作的列名称（英文半角逗号分隔） -->
        <property name="incrementsColumns" value="field1,field2"/>
    </table>
</xml>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 在构建更新对象时，配置了增量支持的字段会增加传入增量枚举的方法
        Tb tb = new Tb.Builder()
                .id(102)
                .field1(1, Tb.Builder.Inc.INC)  // 字段1 统计增加1
                .field2(2, Tb.Builder.Inc.DEC)  // 字段2 统计减去2
                .field4(new Date())
                .build();
        // 更新操作，可以是 updateByExample, updateByExampleSelective, updateByPrimaryKey
        // , updateByPrimaryKeySelective, upsert, upsertSelective等所有涉及更新的操作
        this.tbMapper.updateByPrimaryKey(tb);
    }
}
```
### 15. 查询结果选择性返回插件
一般我们在做查询优化的时候会要求查询返回时不要返回自己不需要的字段数据，因为Sending data所花费的时间和加大内存的占用
，所以我们看到官方对于大数据的字段会拆分成xxxWithBLOBs的操作，但是这种拆分还是不能精确到具体列返回。  
所以该插件的作用就是精确指定查询操作所需要返回的字段信息，实现查询的精确返回。  

插件：
```xml
<xml>
    <!-- 查询结果选择性返回插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.SelectSelectivePlugin" />
</xml>
```
使用：  
```java
public class Test {
    public static void main(String[] args) {
        // 查询操作精确返回需要的列
        this.tbMapper.selectByExampleSelective(
            new TbExample()
            .createCriteria()
            .andField1GreaterThan(1)
            .example(),
            Tb.Column.field1,
            Tb.Column.field2
        );
        // 同理还有 selectByPrimaryKeySelective，selectOneByExampleSelective（SelectOneByExamplePlugin插件配合使用）方法
    }
}
```
### 16. 官方ConstructorBased配置BUG临时修正插件
当javaModelGenerator配置constructorBased=true时，如果表中只有一个column类型为“blob”时java model没有生成BaseResultMap对应的构造函数，
这个bug已经反馈给官方[issues#267](https://github.com/mybatis/generator/issues/267)。  
> 官方V1.3.6版本将解决这个bug,老版本的可以使用这个插件临时修正问题。  

插件：
```xml
<xml>
    <!-- 官方ConstructorBased配置BUG临时修正插件 -->
    <plugin type="com.itfsw.mybatis.generator.plugins.ConstructorBasedBugFixPlugin" />
</xml>
```
