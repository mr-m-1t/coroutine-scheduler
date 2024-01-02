const config = {
  tagFormat: "${version}",
  plugins: [
    // default plugins
    ["@semantic-release/commit-analyzer", {
      preset: "conventionalcommits"
    }],
    ["@semantic-release/release-notes-generator", {
      preset: "conventionalcommits"
    }],
    ["@semantic-release/github", {
      addReleases: "bottom",
    }],
    // additional plugins
    "@semantic-release/changelog",
    // TODO: setup a template file for README.md
    "@semantic-release/exec",
    ["@semantic-release/git", {
      assets: [ "./*.md", "./**/*/.md", "gradle.properties" ],
    }],
    process.env.PUBLISH_DRY_RUN == true ? [] : "gradle-semantic-release-plugin",
  ],
  dryRun: process.env.PUBLISH_DRY_RUN == true,
}

module.exports = config
