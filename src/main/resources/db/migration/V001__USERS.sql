CREATE SEQUENCE SEQUENCE_USERS NOCACHE;
-- CREATE TABLE
CREATE TABLE USERS
(
  ID            NUMBER NOT NULL,
  EMAIL         VARCHAR2(255) NOT NULL,
  NAME          VARCHAR2(255) NOT NULL,
  PASSWORD      VARCHAR2(255) NOT NULL,
  TYPE_USER     VARCHAR2(15) NOT NULL,
  STATUS        VARCHAR2(15) NOT NULL,
  CREATION_DATE DATE DEFAULT SYSDATE,
  UPDATE_DATE   DATE DEFAULT SYSDATE
);
-- CREATE/RECREATE PRIMARY, UNIQUE AND FOREIGN KEY CONSTRAINTS 
ALTER TABLE USERS
  ADD CONSTRAINT PK_USERS PRIMARY KEY (ID)
  USING INDEX;
ALTER TABLE USERS
  ADD CONSTRAINT UK_USERS UNIQUE (EMAIL)
  USING INDEX;

INSERT INTO USERS
  (ID,
   EMAIL,
   NAME,
   PASSWORD,
   TYPE_USER,
   STATUS)
VALUES
  (0,
   'EDER@EVOLUTIONSISTEMAS.COM.BR',
   'EDER@NOVAPRODUTIVA.COM.BR',
   '$2a$10$RaI5.M7R2YSJMRkZnZF6/uQ5C8X2BPkOMRwqpOSNGCSY10BjVNjzS',
   'ADMIN',
   'ACTIVE');  