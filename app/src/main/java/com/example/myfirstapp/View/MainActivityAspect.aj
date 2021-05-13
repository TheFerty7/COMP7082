public aspect MainActivityAspect {
    MainActivity main = new MainActivity()

    pointcut callCheckCaption() :
     call(boolean com.example.myfirstapp.View.MainActivity.checkCaption());

    before() : callCheckCaption() {
    }

    boolean around() :
      callCheckCaption() {
        return true;
    }

    after() : callCheckCaption() {
    }
}