# Hash Functions Project

## Building

```
cd <project_folder>
ant compile
```

## Running

```
java -jar name-of-jarfile.jar
```

or

```
ant run -Darg0 name-of-classfile
```

To run individual files

```
java -cp build <classname>
```

## TODO

Any time a bit that is a multiple of 8 is flipped, it causes a NumberFormatException, and the flip count for that entire iteration is not incremented. Everything still works  fine, however I am not sure if it will throw off the stats (average, etc.). (There's always the change Collins expects this anyway and it won't matter, but I will have to ask him).
