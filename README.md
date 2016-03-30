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

- Verify that the `diff` method works correctly (two ways of implementing it, have to ask Dr. Collins)
- Verify that `flip` method is acceptable (technically returns response that Collins wants, but it was implemented differently than how it *should* have been)