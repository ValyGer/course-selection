drop table IF EXISTS REGISTRATIONS;
drop table IF EXISTS STUDENTS;
drop table IF EXISTS COURSES;


create TABLE STUDENTS (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  FIRSTNAME VARCHAR(128) NOT NULL,
  LASTNAME VARCHAR(128) NOT NULL,
  PRIMARY KEY (ID)
);


create TABLE COURSES (
  ID BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
  TITLE VARCHAR(64) NOT NULL,
  LIMIT_PERSON BIGINT NOT NULL,
  START_REG TIMESTAMP WITH TIME ZONE,
  FINISH_REG TIMESTAMP WITH TIME ZONE,
  PRIMARY KEY (ID)
);


create TABLE REGISTRATIONS (
  COURSE_ID BIGINT,
  STUDENT_ID BIGINT
);


alter table REGISTRATIONS add CONSTRAINT FK_REGISTRATIONS_COURSE  FOREIGN KEY (COURSE_ID) REFERENCES COURSES (ID) ON delete CASCADE;

alter table REGISTRATIONS add CONSTRAINT FK_REGISTRATIONS_STUDENT  FOREIGN KEY (STUDENT_ID) REFERENCES STUDENTS (ID) ON delete CASCADE;