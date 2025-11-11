## Overview
This document outlines my individual plan for developing the **Login and Authentication System** for our CSC207 project *Alive Rails*

### Goal
Implement an **email/password-based**(perhaps it could be improved if there's enough time) authentication system

using Firebase REST API, fully integrated with the existing `ViewManager` architecture

---

## TODOs
- Design and implement the `LoginView` class under `view/`
- Connect `LoginController`, `LoginInteractor`, and `FirebaseAuthManager`
- Handle both **Login** and **Register** flows
- Store and manage authenticated user data (`PlayerSession`)
- Coordinate with Cayden (Rendering) to integrate user login before entering `GameView`

---

##  Timeline
| Step | Task                                            | Deliverable                        |
|------|-------------------------------------------------|------------------------------------|
| 1    | Research Firebase REST API & setup test project | `FirebaseAuthManager.java` working |
| 2    | Implement `LoginView` UI                        | UI prototype visible in game       |
| 3    | Connect Login flow with `ViewManager`           | Login success triggers `GameView`  |
| 4    | Add Register + Error handling                   | Dual-mode login/register screen    |
| 5    | (Optional) Integrate leaderboard authentication | Sync score with UID                |

---

## Potential Challenges
- Handling API exceptions / offline mode
- Parsing JSON responses without adding heavy libraries
- Making UI responsive in LibGDX
- Synchronizing PlayerSession with backend saves

---

##  Next Steps
- [ ] Confirm API key setup with the team Firebase project
- [ ] Start implementing `LoginView`
- [ ] Merge base class skeletons (`LoginInteractor`, `LoginController`) into main branch

---

**Last Updated:** November 7, 2025,  
**GitHub Repo:** [https://github.com/KurbyDoo/ZombieExpress](https://github.com/KurbyDoo/ZombieExpress)
**API:** [https://firebase.google.com](https://firebase.google.com)
