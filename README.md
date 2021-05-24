# SeleniumTestWebstaurant
3 parsed out small tests for Webstaurant.

- A Chrome Browser instance is opened. The term 'stainless work table' is searched for in the top bar.
- Numerous search results are expected. Selenium verifies that every product has the word 'Table' in it.
- The search result set is so large that Selenium needs the browser to click to the last available page.
- Once there, it will find the last "Add to Cart" button, and click it.
- A small modal will appear, allowing the user to see the current status of their shopping cart.
- Once the modal is clicked, the user will be taken to the /viewcart.cfm page
- Selenium will then attempt to click the "Empty Cart" button and confirm.
- When the text "Your cart is empty." appears, the test(s) will have all passed.
