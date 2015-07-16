# jsearchgen

Prototyping a dungeon generator with constructing guidance based on multi objective fitness.

## How to

Instructions to generate the project and run the tests with Eclipse

### Debian-based systems

Install gradle build system:

```
sudo apt-get install gradle
```

Clone repository:

```
git clone https://github.com/alesegdia/jsearchgen.git
```

Generate eclipse project:

```
cd jsearchgen/project
gradle eclipse
```

Finally, open eclipse and import the project and that's it. Now you can run the tests
at `com.alesegdia.jsearchgen.test` package.

