# drools

### 适用场景
* 风险控制系统 -- 风险贷款、风险评估
* 反欺诈项目 -- 银行贷款、征信验证
* 决策平台系统 -- 财务计算
* 促销平台系统 -- 满减、打折、加价购
* 物联网系统 -- ？

### 规则引擎构成
* 工作内存 (Working Memory)
* 规则库 (Rule Base)
* 推理引擎 (Inference Engine)

#### 推理引擎(Inference Engine) 包括
* 匹配器 (Pattern Matcher)
* 议程 (Agenda)
* 执行引擎 (Execution Engine)

#### 工作内存 (Working Memory)
> drools会从工作内存(Working Memory)中获取数据并和规则文件中定义的规则进行模式匹配，应用程序只需要将数据插入Working Memory中

#### 其他概念
* **Fact ：** 事实，Fact对象是我们的应用和规则引擎进行数据交互的桥梁或通道；在drools中，将一个普通的JavaBean插入到Working Memory后的对象就是Fact对象
* **Rule Base ：** 规则库，我们在规则文件中定义的规则都会被加载到规则库中。
* **Pattern Matcher：** 匹配器，将Rule Base中的所有规则与Working Memory中的Fact对象进行模式匹配，匹配成功的规则将被激活并放入议程(Agenda)中。
* **Agenda：** 议程，用于存放通过匹配器进行模式匹配后被激活的规则。
* **Execution Engine：** 执行引擎，执行议程(Agenda)中被激活的规则。

### 规则引擎执行过程
1. 将初始数据(Fact)输入至工作内存(Working Momory)
2. 使用**匹配器(Pattern Matcher)** 将规则库中的规则(rule)和数据(fact)比较
3. 如果执行规则存在冲突(conflict)，如 同时激活了多个规则，将冲突的规则放入冲突集合
4. 解决冲突，将激活的规则按顺序放入**议程(Agenda)** 
5. 执行议程(Agenda)中的规则，重复步骤 4->5 ，直至执行完议程中所有规则


### drools API 开发步骤：
1. 获取 KieServices
2. 获取 KieContainer
3. KieSession
4. Insert fact
5. 触发规则
6. 关闭 KieSession

_注：KIE 知识就是一切(缩写)_ 

### 规则文件
* package: 包名，只限于逻辑上的管理，同一个包名下的查询或者函数可以直接调用    
* import: 用于导入类或者静态方法
* global：全局变量
* function：自定义函数
* query：查询
* rule end：规则体
  * rule：关键字，表示规则开始，参数为规则的唯一名称。
  * attributes：规则属性，是rule与when之间的参数，为可选项。
  * when：关键字，后面跟规则的条件部分。
  * LHS(Left Hand Side)：是规则的条件部分的通用名称。它由零个或多个条件元素组成。如果LHS为空，则它将被视为始终为true的条件元素。
  * then：关键字，后面跟规则的结果部分。
  * RHS(Right Hand Side)：是规则的后果或行动部分的通用名称。
  * end：关键字，表示一个规则结束。

_Drools支持的规则文件，除了drl形式，还有Excel文件类型的。_

### Pattern模式匹配
> Drools中的匹配器可以将Rule Base中的所有规则与Working Memory中的Fact对象进行模式匹配，那么我们就需要在规则体的LHS部分定义规则并进行模式匹配。 LHS部分由一个或者多个条件组成，条件又称为pattern

pattern的语法结构为：绑定变量名:Object(Field约束)
