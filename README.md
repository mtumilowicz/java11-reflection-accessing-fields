[![Build Status](https://travis-ci.com/mtumilowicz/java11-reflection-accessing-fields.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-reflection-accessing-fields)

# java11-reflection-accessing-fields

# preface
Using reflection we could read or set the value of a field of an object.

To read/set the value of a field, we have to:
1. Get reference to the field: https://github.com/mtumilowicz/java11-reflection-fields
1. To read the value, we have to call a proper method from `Field` class:
    * if the field is primitive
        ```
        public XXX getXXX(Object obj)
        ```
        * `XXX` is the primitive type
        * `obj` the object to extract the value from
        * exceptions:
            * `IllegalAccessException` - if the underlying
              field is inaccessible (enforcing Java language access control).
            * `IllegalArgumentException` - if the specified object is not
              an instance of the class or interface declaring the
              underlying field (or a subclass or implementor
              thereof), or if the field value cannot be
              converted to the type by a
              widening conversion.
            * `NullPointerException` - if the specified object is `null`
              and the field is an instance field.
            * `ExceptionInInitializerError` if the initialization provoked
              by this method fails.
    * if the field is not primitive
        ```
        public Object get(Object obj)
        ```
        * `obj` object from which the represented field's value is
          to be extracted
        * exceptions:
            * `IllegalAccessException` - if underlying 
            field is inaccessible (enforcing Java language access control).
            * `IllegalArgumentException` - if the specified object is not an
              instance of the class or interface declaring the underlying
              field (or a subclass or implementor thereof).
            * `NullPointerException` - if the specified object is `null`
              and the field is an instance field.
            * `ExceptionInInitializerError` if the initialization provoked
              by this method fails.
1. To set the value, we have to call a proper method from `Field` class:
    * if the field is primitive
        ```
        public void setXXX(Object obj, XXX z)
        ```
        * `XXX` is the primitive type
        * `obj` the object whose field should be modified
        * `z` the new value for the field
        * exceptions:
            * `IllegalAccessException` - the underlying 
            field is either inaccessible or final (enforcing Java language access control).
            * `IllegalArgumentException` - if the specified object is not an
                  instance of the class or interface declaring the underlying
                  field (or a subclass or implementor thereof),
                  or if an unwrapping conversion fails.
            * `NullPointerException` - if the specified object is `null`
                  and the field is an instance field.
            * `ExceptionInInitializerError` if the initialization provoked
                  by this method fails.
    
    * if the field is not primitive
        ```
        public void set(Object obj, Object value)
        ```
        * `obj` the object whose field should be modified
        * `value` the new value for the field
        * exceptions:
            * `IllegalAccessException` - 
                if the underlying field is either inaccessible
                or final (enforcing Java language access control).
            * `IllegalArgumentException` - if the specified object is not an
              instance of the class or interface declaring the underlying
              field (or a subclass or implementor thereof),
              or if an unwrapping conversion fails.
            * `NullPointerException` - if the specified object is `null`
              and the field is an instance field.
            * `ExceptionInInitializerError` - if the initialization provoked
              by this method fails.
            
* static and instance fields are accessed the same way (in case
    of static fields the `obj` is ignored)
* **we can access only that fields which we can access 
  with regular java code (otherwise `IllegalAccessException`), 
  we can bypass it using**: https://github.com/mtumilowicz/java11-deep-reflection
      
# project description
We will show how to access fields using reflection.
```
class X {
    private String privateField = "a";
    int count;
    final String name = "string";
}
```
and accessing:
* primitive
    * get
        ```
        var count = X.class.getDeclaredField("count").getInt(new X());
        
        assertThat(concatenated, is(0));
        ```
    * set
        ```
        var x = new X();
        X.class.getDeclaredField("count")
                .setInt(x, 1);
        
        assertThat(x.count, is(1));
        ```
* object
    * get
        ```
        var concatenated = (String) X.class.getDeclaredField("name")
                .get(new X());
        
        assertThat(concatenated, is("string"));
        ```
    * set
        ```
        var x = new X();
        X.class.getDeclaredField("notFinal")
                .set(x, "set");

        assertThat(x.notFinal, is("set"));
        ```
**pay attention to types:**
* get
    ```
    @Test(expected = IllegalArgumentException.class)
    public void accessing_badType() throws NoSuchFieldException, IllegalAccessException {
        X.class.getDeclaredField("name").getInt(new X());
    }
    ```
* set
    ```
    @Test(expected = IllegalArgumentException.class)
    public void set_wrongType() throws NoSuchFieldException, IllegalAccessException {
        X.class.getDeclaredField("notFinal")
                .setInt(new X(), 1);
    }
    ```
**pay attention to accessibility:**
* get
    ```
    @Test(expected = IllegalAccessException.class)
    public void accessing_private() throws NoSuchFieldException, IllegalAccessException {
        X.class.getDeclaredField("privateField").get(new X());
    }
    ```
* set
    ```
    @Test(expected = IllegalAccessException.class)
    public void set_finalString() throws NoSuchFieldException, IllegalAccessException {
        var x = new X();
        X.class.getDeclaredField("name")
                .set(x, "set");

        assertThat(x.name, is("set"));
    }
    ```