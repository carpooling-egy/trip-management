# 🚘 Trip Management Service

The Trip Management Service is responsible for aggregating and presenting a unified view of a user's upcoming trips. It integrates with the **Profile Service** to enrich each trip with relevant and user-friendly details.

---

## 📌 Responsibilities

- 🔄 Fetch all **upcoming trips** for a user (including both ride requests and offers)
- 🗂️ Return **summarized trip cards** for quick previews
- 🔍 Return **detailed trip cards** with complete trip information

---

## 🔁 Interactions

- 📥 Fetches **ride requests** and **ride offers** from the internal trip database
- 🧠 Calls the **Profile Service** to enrich trips with user profile data (e.g., driver/passenger info)
- 🌐 Exposes **REST API endpoints** (via the **API Gateway**) to the user-facing application for viewing upcoming trips


---

## 📣 Maintainers

This service is part of the **3alsekka Carpooling System** (Graduation Project - Alexandria University, 2025).
