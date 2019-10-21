# aihulk 决策引擎

## 1.什么是aihulk决策引擎？
### aihulk决策引擎是一款适用于多个行业领域的规则引擎，具有易使用，灵活，支持多种规则资源的特点。
### 2.名词解释
### (1)事实：事实简单来说就是描述一个事物的属性，比如说一个人的年龄，姓名，身高都可以作为一个事实，在具体业务场景中，需要关注的点都可以作为一个事实
### (2)表达式：表达式就是根据事实我们可以做一些计算，比如说我们要判断一个人年龄大于30，那么表达式就是 年龄事实>30，这就是一个简单的表达式
### (3)决策单元：决策单元是由一系列的表达式组成，并且当表达式中的条件最终执行结果满足条件时，就会触发决策单元的动作。
### 比如说现在有一个决策单元是这样配置的：如果 年龄<30 且 资产>100w 我们就触发一个动作，假设这个场景是金融贷款的场景，那么我们就会作出批贷或者走下一步审核的操作
### 决策单元支持多种执行方式，目前支持决策块，决策表，评分卡，决策树
### (4)决策链：决策链主要用于控制多个决策单元的流程，在决策链中，我们甚至可以控制决策单元之间的跳转，例如我们有三个决策单元A、B、C，假设在执行A过后我们需要根据A执行后的结果再去判定执行B还是C，这个都交由决策链统一控制。简单来说，决策链可以对决策单元做流程控制
### 3.如果对决策引擎需要商业支持，可通过如下方式联系我们：
### email:aihulk@gmail.com
### mobile:15226508375
### 4.贡献者
#### yibozhang:https://github.com/juliuszhang
#### haolin:https://github.com/ththinking
#### dongdongguo:https://github.com/gordonghust
