# ArtSpace-AndroidBasics-
  仅使用了课程学习到的知识制作的简单应用，运行结果如图：

  <img width="301" height="649" alt="image" src="https://github.com/user-attachments/assets/ac657cab-a144-4252-9d3e-35f928a893a8" />
  
  点击按钮可以切换前后的作品，当切至最后一张作品时，再点击下一张，将跳转至第一张，如此循环，点击上一张也是如此。
  
  这里使用了一个索引变量，利用自定义的委托类实现数字值的范围控制，又因为一个变量只能被一个类委托，自定义的委托类就接收MutableState类的变量，这样保证了该变量既能在自增自减时无需担心越界问题，也能如预期触发Compose组件的重组从而完成作品的切换。
  
  委托类的定义如图：

  <img width="705" height="595" alt="image" src="https://github.com/user-attachments/assets/e13be858-3f38-4e3f-916c-2b8f587835d1" />
  
