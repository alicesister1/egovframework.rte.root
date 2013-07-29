drop table jobhist;

drop table emp;

drop table dept;

CREATE TABLE dept (
    dept_no          NUMBER(2) NOT NULL,
    dept_name        VARCHAR(14),
    loc             VARCHAR(13),
    CONSTRAINT dept_pk PRIMARY KEY (dept_no),
    CONSTRAINT dept_name_uq UNIQUE (dept_name)
);

CREATE TABLE emp (
    emp_no          NUMBER(4) NOT NULL,
    emp_name        VARCHAR(10),
    job             VARCHAR(9),
    mgr             NUMBER(4),
    hire_date       DATE,
    sal             NUMBER(7,2),
    comm            NUMBER(7,2),
    dept_no         NUMBER(2),
	CONSTRAINT emp_pk PRIMARY KEY (emp_no),
	CONSTRAINT emp_sal_ck CHECK (sal > 0),
	CONSTRAINT emp_ref_dept_fk FOREIGN KEY (dept_no) REFERENCES dept(dept_no)
);

CREATE TABLE jobhist (
    emp_no           NUMBER(4) NOT NULL,
    start_date       DATE NOT NULL,
    end_date         DATE,
    job             VARCHAR(9),
    sal             NUMBER(7,2),
    comm            NUMBER(7,2),
    dept_no          NUMBER(2),
    chg_desc         VARCHAR(80),
    CONSTRAINT jobhist_pk PRIMARY KEY (emp_no, start_date),
    CONSTRAINT jobhist_ref_emp_fk FOREIGN KEY (emp_no)
        REFERENCES emp(emp_no) ON DELETE CASCADE,
    CONSTRAINT jobhist_ref_dept_fk FOREIGN KEY (dept_no)
        REFERENCES dept (dept_no) ON DELETE SET NULL,
	CONSTRAINT jobhist_date_chk CHECK (start_date <= end_date)
);

drop table emp_copy;

CREATE TABLE emp_copy (
    emp_no          NUMBER(4) NOT NULL,
    emp_name        VARCHAR2(10),
    job             VARCHAR2(9),
    mgr             NUMBER(4),
    hire_date       DATE,
    sal             NUMBER(7,2),
    comm            NUMBER(7,2),
    dept_no         NUMBER(2)
);
