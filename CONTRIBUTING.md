# How to contribute
:sparkles: Thank you for contributing! :sparkles:

There are a few guidelines to help keep the project consistent, which reduces the maintenance effort.

## Creating an issue
Before creating an issue:
1. Please make sure the bug/feature/etc. is not already addressed by an [existing issue](https://github.com/mr-m-1t/coroutine-scheduler/issues)
2. When creating a bug report, include the steps to reproduce the issue. Logs and/or stack traces are helpful!
3. When making a feature request, please share the motivation for the new feature and any thoughts on API surface

## Making changes
- Create a topic branch from the `master` branch
- Keep git commit messages clear and appropriate
  - This project makes use of [semantic-release](https://semantic-release.gitbook.io/semantic-release/), so please follow the [conventional commit](https://www.conventionalcommits.org/en/v1.0.0/) format
  - There is no need to bump the version in [gradle.properties](gradle.properties)
- Ensure any changes have unit tests
- Add/update [KDoc](https://kotlinlang.org/docs/kotlin-doc.html) as appropriate
  - All public methods/classes/etc. should have documentation
  - https://kotlinlang.org/docs/coding-conventions.html
- This project uses [ktlint](https://github.com/pinterest/ktlint) and [spotless](https://github.com/diffplug/spotless) to ensure consistent formatting
  - Lint fixes are automatically applied when the project is built :tada:
  - There is an [.editorconfig](https://editorconfig.org/) file that should bring IDE auto-formatting in-line with ktlint
- When submitting a PR be sure to tag any issues the PR is related to and/or closing
  - https://docs.github.com/en/issues/tracking-your-work-with-issues/linking-a-pull-request-to-an-issue

