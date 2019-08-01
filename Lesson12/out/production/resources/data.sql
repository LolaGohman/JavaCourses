INSERT INTO users
  (username, password, role)
SELECT 'admin', 'a59fde4903ac1c03537ae9b40fddd6ec400ba8f61b56eafad891b4840dc5b7549c4c9f55bbcbae00','ADMIN'
WHERE NOT EXISTS(
    SELECT username FROM users WHERE username = 'admin'
  );


