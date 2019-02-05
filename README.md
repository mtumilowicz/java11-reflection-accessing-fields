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
    * static and instance fields are accessed the same way (in case
        of static fields the `obj`)
    * **we can access only that fields which we can access 
      with regular java code (otherwise `IllegalAccessException`), 
      we can bypass it using**: https://github.com/mtumilowicz/java11-deep-reflection
      
# project description