# Workflow

## Issues & milestones

We have used the GitLab issue tracker to keep track of all our issues and milestones. We used issues as a starting point for all work that had to be done. Each milestone in the repository corresponts to each release of the project, so that we can easliy keep track of what needs to be done and how well on track we are for each release. The milestone progress bar worked as an indicator for how much work we had left to do for each release.

Each issue has a description of what needs to be done, and a number that we use to refer to the issue in the commit messages. This makes it easy to keep track of what has been done and what is left to do.

Issue labels has been heavily used to keep track of what types of issues we have. We have used the following labels (the names speak for themselves):

- `Backend`
- `Frontend`
- `Documentation`
- `Testing`
- `Bugfix`
- `Code quality`
- `Database`
- `Priority`

## Commits & commit messages

<!-- 

We have used the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) standard for our commit messages. This means that every commit message has a header, body and footer. The header has a type, scope and subject.

The subject is a short description of the commit. It should be written in the imperative, and not capitalized. It should not be longer than 50 characters.

The body is optional, and can be used to further explain what the commit does. It should not be longer than 72 characters.

The footer is optional, and can be used to reference issues that the commit closes. It should be written like this: `Closes #<issue number>`.
-->

## Branches & merging

We have mainly used one branch for each issue. Each branch in the repository has a name that starts with the issue number, so that we can easily see what issue the branch is related to. The branch name also contains a short description of what the branch is about.

An important principle that we have followed is that we have one safe branch: master. There is not possible to work directly in the master branch and push to it. The master branch is only updated by merging from other branches. 

## Pipeline

We have used the GitLab CI/CD
**pipeline** to run our tests and check our code quality so that we cannot merge without a quality check. We have also used the GitLab merge request feature to ensure that we do not push directly to the master branch.

## Code quality

<!-- Plugins -->

## Pair programming

Pair programming is an essential part of our workflow, we pair program to ensure that we understand the code and to learn from each other. It is also helpful to cooperate with someone else when solving a difficult problem.

## Planning

We have two weekly meetings where we discuss our progress and what we need to do to finish the project, those meetings also consists of us coding together for several hours. If we see that two times a week is not enough, we will add more meetings. To ensure that most of us is present at each meeting, we have made a contract that we gives fines to each person that are late to the meetings, this has worked very well since it is a more motivating factor to be on time to the meetings.

## Maven

<!-- Like we did when working with release 2, we used the GitLab issue tracker to keep track of our issues and milestones. We still used the GitLab CI/CD pipeline to run our tests and check our code quality, but we made some changes in the pipeline jobs (listed above). We used the GitLab merge request feature to ensure that we do not push directly to the master branch.

For this release we had atleast two meetings a week, where we discuss our progress and what we need to do to finish the project. Those meetings also consists of us coding together for several hours.

We have used the GitLab issue tracker to keep track of our issues and milestones. -->