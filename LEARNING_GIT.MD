# LEARNING-GIT.md

This document describes how you will learn Git and GitHub gradually during the first weeks of CMPS 357. The emphasis is on using GitHub as the primary interface for understanding version control, with local tools serving that larger workflow.

---

## Phase 1: Understanding Git through cloning and local commits

**Focus:** What Git tracks and why commits matter.

At the start of the semester, you will work with instructor-provided repositories that you can clone but not push to. During this stage, Git is used only to manage changes locally.

### What you will practice
- Cloning an existing GitHub repository.
- Making small, intentional changes to code or documentation.
- Creating local commits that:
  - represent a single logical change
  - have clear, descriptive commit messages
- Viewing:
  - file differences
  - commit history
  - how code evolves over time

### What you are learning
- Git records *changes*, not files.
- A commit is a snapshot with a purpose.
- Commit history tells the story of how a solution was built.

At this stage, your work stays local. The goal is understanding, not publishing.

---

## Phase 2: Working with forks and pushing to GitHub

**Focus:** Turning local work into shareable work.

Once you are comfortable making commits, you will begin working with your own GitHub repositories by forking instructor projects.

### What you will practice
- Forking a repository on GitHub (creating your own server-side copy).
- Cloning your fork to your machine.
- Making and committing changes locally as before.
- Pushing commits to your fork on GitHub.
- Reviewing your work using GitHub’s web interface:
  - commit lists
  - diffs
  - rendered README files

### What you are learning
- GitHub hosts repositories and histories.
- Forks allow independent work without affecting the original project.
- Pushing publishes your work and makes it visible.

At this point, GitHub becomes the primary place where you review and verify your work.

---

## Phase 3: Building projects incrementally with branches

**Focus:** Managing growth and change in a real project.

As projects become larger, you will begin creating repositories of your own and using branches to organize work.

### What you will practice
- Starting a repository with only a `README.md`.
- Building out project structure over time:
  - adding source folders
  - adding configuration files
  - introducing new features gradually
- Using branches on GitHub to isolate changes:
  - one branch per feature or fix
- Merging branches back into `main` once a feature is complete.

### What you are learning
- The `main` branch represents stable, working code.
- Branches allow experimentation without risk.
- Merging is a deliberate act that integrates change.

You will often review and merge branches using GitHub’s interface, mirroring professional workflows.

---

## Phase 4 and beyond: Professional GitHub workflows

**Focus:** Clarity, review, and collaboration.

As the semester continues, Git and GitHub will become part of your normal development process.

### What you will practice
- Writing commit messages that explain intent, not just mechanics.
- Using pull requests to:
  - describe changes
  - review diffs before merging
  - reflect on design decisions
- Managing multiple features and fixes over time.

### What you are learning
- Git history is a form of documentation.
- GitHub supports collaboration, review, and accountability.
- Version control enables confident change, not just backup.

---

## The big picture

By learning Git and GitHub gradually:
- You avoid tool overload early in the semester.
- Each new concept builds on something familiar.
- Version control becomes a thinking tool, not just a requirement.

By the end of the course, you should be comfortable using GitHub as the central hub for your software development work, both individually and in teams.
