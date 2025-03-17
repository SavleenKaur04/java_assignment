const { Octokit } = require("@octokit/rest");
const axios = require("axios");
require("dotenv").config();

const octokit = new Octokit({ auth: process.env.GITHUB_TOKEN });

async function postComment(owner, repo, pull_number, comment) {
    await octokit.issues.createComment({
        owner,
        repo,
        issue_number: pull_number,
        body: comment,
    });
}

async function fetchSonarCloudIssues() {
    const sonarURL = `https://sonarcloud.io/api/issues/search?componentKeys=${process.env.SONAR_PROJECT_KEY}`;
    const response = await axios.get(sonarURL, {
        headers: { "Authorization": `Bearer ${process.env.SONAR_TOKEN}` },
    });

    return response.data.issues.map(issue =>
        `🔴 Issue: ${issue.message} (File: ${issue.component})`
    ).join("\n");
}

async function main() {
    const comments = await fetchSonarCloudIssues();
    await postComment("your-username", "your-repo", 1, comments);
}

main();
