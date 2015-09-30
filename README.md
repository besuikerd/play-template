# Play Template

## Creating a new module

```
activator createModule [module_name]
```

Add the module to the main build file `build.sbt`:

```
lazy val [module_name] = (project in file("modules/[module+name]")
  .dependsOn(common)
```

Add a dependency to the common project:

```
.dependsOn([module_name]).aggregate([module_name])
```

Add the module to the reverse router in common:

```aggregateReverseRoutes := Seq(..., [module_name])```

Add a route for the module in `conf/routes`:

```-> /[module_name]                     [module_name].Routes```

If you are using IntelliJ you might want to add the folder `modules/common/target/scala-2.11/classes` as a dependency for the module, by going to File → Project Structure → Modules → [module_name] and selecting the Dependencies tab. Press the green plus, then select JARs or directories... and select the beforementioned folder. This enables IntelliJ to detect reverse routers on your classpath.
