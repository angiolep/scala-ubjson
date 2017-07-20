import Common._

val core = project
  .settings(coreSettings: _*)

val examples = project
  .settings(coreSettings: _*)
  .dependsOn(core)

val docs = project
  .settings(coreSettings: _*)
  .dependsOn(core)

val ubjson = (project in file("."))
  .aggregate(core, examples, docs)


