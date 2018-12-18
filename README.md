# AndElm

[Elm Architecture](https://guide.elm-lang.org/architecture/) 在Android上的实现.

Elm 自带了一个Virtual Dom实现，所以View是声明式的。Virtual Dom在Android/Java技术栈中并没有非常好的对应实现。
但是View 只是Elm Arch的一个组成部分，没有Virtual Dom实现并不会影响Elm Arch的核心思路。

本项目使用EAndroid/Java 技术栈实现了Elm架构。
1. Elm Arch
2. 基于xml布局 + 代码绑定实现声明式UI


# Demo

实现一个Elm Architecture +1 -1 Button的demo

```java

public class Number implements Demo {


    private static final String INC = "inc";
    private static final String DEC = "dec";

    ///////////////////////////////////////////////////////////////////////////
    // main
    ///////////////////////////////////////////////////////////////////////////

    public View main(Context context) {
        final View root = View.inflate(context, R.layout.elm_number, null);
        Elms.construct(context, initUpdate(), 0, initView(root));
        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    // update
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private Elm.Update<Integer> initUpdate() {
        final CompoundUpdate<Integer> update = new CompoundUpdate<>();
        update.add(INC, (action, param, old) -> old + 1);
        update.add(DEC, (action, param, old) -> old - 1);
        return update;
    }

    ///////////////////////////////////////////////////////////////////////////
    // view
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private ReactiveViewFactory<Integer> initView(View root) {
        return (context, elm, bind) -> {

            final Bind<Integer> $ = new Bind<>(bind);

            final View inc = root.findViewById(R.id.add_1);
            inc.setOnClickListener(v -> elm.send(INC));

            final View dec = root.findViewById(R.id.minus_1);
            dec.setOnClickListener(v -> elm.send(DEC));

            final TextView number = root.findViewById(R.id.number);
            $.text(number, this::strOfInt);
        };
    }

    private String strOfInt(Integer v) {
        return String.valueOf(v);
    }
}

```

#### 表单demo

```java
public class Forms implements Demo {

    static final String OK = "OK";

    ///////////////////////////////////////////////////////////////////////////
    // main
    ///////////////////////////////////////////////////////////////////////////

    @Override
    public View main(Context context) {
        final View root = View.inflate(context, R.layout.elm_forms, null);
        Elms.construct(context, initUpdate(), new Form(), initView(root));
        return root;
    }

    ///////////////////////////////////////////////////////////////////////////
    // model
    ///////////////////////////////////////////////////////////////////////////

    static class Form {

        String name = "";
        String password = "";
        String passwordAgain = "";
        String status = OK;
    }

    ///////////////////////////////////////////////////////////////////////////
    // update
    ///////////////////////////////////////////////////////////////////////////

    private static final String KEY_NAME = "NAME";
    private static final String KEY_PASSWORD = "PASSWORD";
    private static final String KEY_AGAIN = "AGAIN";

    @NonNull
    private Elm.Update<Form> initUpdate() {
        final CompoundUpdate<Form> update = new CompoundUpdate<>();
        update.add(KEY_NAME, (MutableUpdate<Form>) (action, param, model) -> model.name = ((String) param));
        update.add(KEY_PASSWORD, (MutableUpdate<Form>) (action, param, model) -> model.password = ((String) param));
        update.add(KEY_AGAIN, (MutableUpdate<Form>) (action, param, model) -> {
            model.passwordAgain = ((String) param);
            model.status = model.password.equals(model.passwordAgain) ? OK : "PASSWORD DO NOT MATCH";
        });
        return update;
    }

    ///////////////////////////////////////////////////////////////////////////
    // view
    ///////////////////////////////////////////////////////////////////////////

    @NonNull
    private ReactiveViewFactory<Form> initView(View root) {
        return (context, elm, bind) -> {
            EditText name = root.findViewById(R.id.input_name);
            name.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    elm.send(KEY_NAME, s.toString());
                }
            });

            EditText password = root.findViewById(R.id.input_password);
            password.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    elm.send(KEY_PASSWORD, s.toString());
                }
            });

            EditText again = root.findViewById(R.id.input_password_again);
            again.addTextChangedListener(new SimpleTextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {
                    elm.send(KEY_AGAIN, s.toString());
                }
            });

            final Bind<Form> $ = new Bind<>(bind);

            TextView status = root.findViewById(R.id.text);
            $.text(status, form -> form.status);
            $.textColor(status, form -> OK.equalsIgnoreCase(form.status) ? Color.GREEN : Color.RED);
        };
    }
}

```

### 获取

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:
```grovvy
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2. Add the dependency
```grovvy
dependencies {
    implementation 'com.github.weixinfree:AndElm:Tag'
}
```
