## Initial Prompt for frontend Dev 

Build a complete, end-to-end frontend application for my **DocBrain** backend inside the `frontend/` directory using the latest versions of **React** and **Tailwind CSS**.

### Visual & UI Reference

Match the dark-mode UI, layout, color scheme, typography, and component structure shown in the reference screenshots located in `@frontend/screenshots/`, updating the branding/title from "DocMind" to **DocBrain**. Inspect the existing backend codebase first to align API endpoints, request/response payloads, and environment variables.

### Core Features to Implement

1. **Document Management & Upload (`@frontend/screenshots/img.png` & `@frontend/screenshots/img_1.png`)**:
* Left sidebar displaying the "Knowledge Base" with a drag-and-drop / file-picker upload area supporting single and multiple document uploads (PDF, Word, Markdown, TXT, CSV) with a real-time progress bar.
* Document list displaying file names, file sizes, page counts, chunk counts, and indexing status badges (e.g., `Indexed`), along with a search/filter input (`Filter files...`) and document selection/scope controls (`All Documents` vs. single document).




2. **Prompt Feature Templates (`@frontend/screenshots/img_1.png`)**:
* Empty-state welcome screen for "DocBrain AI Document Assistant" featuring clickable starter prompt cards (e.g., *Summarize Key Points*, *Interview / Q&A Insights*, *Action Items & Deadlines*, *Technical Concepts*) that populate or trigger queries.




3. **Interactive RAG Chat Interface (`@frontend/screenshots/img_2.png`)**:
* Conversational chat view displaying user messages, AI assistant responses with markdown formatting, copy-to-clipboard actions, source citations/page numbers, and a bottom input bar (`Ask a question across all documents...`) supporting `Enter` to send and `Shift+Enter` for newlines.




4. **Semantic Vector Similarity Search Modal (`@frontend/screenshots/img_3.png`)**:
* Modal/dialog (`Semantic Vector Similarity Search`) allowing users to search concepts, clauses, or facts across vector chunks and inspect matching chunk results with similarity scores and metadata.





### Technical Requirements

* Place all frontend source code and configuration inside the `frontend/` folder.
* Use the latest stable versions of React, Vite (or the appropriate modern React build setup), Tailwind CSS, and Lucide icons.
* Integrate cleanly with all existing backend REST APIs, including proper loading states, upload progress tracking, and error handling.