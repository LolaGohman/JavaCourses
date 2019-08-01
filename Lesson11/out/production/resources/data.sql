INSERT INTO users
  (username, password, isAdmin)
SELECT 'admin', '21232f297a57a5a743894a0e4a801fc3', true
WHERE NOT EXISTS(
    SELECT username FROM users WHERE username = 'admin'
  );

