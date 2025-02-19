CREATE SEQUENCE SEQUENCE_PLEACES NOCACHE;
-- CREATE TABLE
CREATE TABLE PLEACES
(
  ID            NUMBER NOT NULL,
  GROUP_ID      NUMBER NOT NULL,
  USER_ID       NUMBER NOT NULL,
  CREATION_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE   DATE DEFAULT SYSDATE
);
-- CREATE/RECREATE INDEXES 
CREATE INDEX FK_PLEACES_GROUPS ON PLEACES (GROUP_ID);
CREATE INDEX FK_PLEACES_USERS ON PLEACES (CREATION_DATE);
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE PLEACES
  ADD CONSTRAINT PK_PLEACES PRIMARY KEY (ID)
  USING INDEX;
ALTER TABLE PLEACES
  ADD CONSTRAINT UK_PLEACES UNIQUE (GROUP_ID, USER_ID)
  USING INDEX;
ALTER TABLE PLEACES
  ADD CONSTRAINT FK_PLEACES_GROUPS FOREIGN KEY (GROUP_ID)
  REFERENCES GROUPS (ID);
ALTER TABLE PLEACES
  ADD CONSTRAINT FK_PLEACES_USERS FOREIGN KEY (USER_ID)
  REFERENCES USERS (ID);