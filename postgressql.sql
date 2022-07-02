begin;
set transaction read write;
alter database d5urrerfbdki23 set default_transaction_read_only = off;
commit;

CREATE TABLE CHUONGTRINH (
	MACT INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	TIEUDECT VARCHAR(200) NOT NULL,
	NGAYBATDAU DATE NOT NULL,
	NGAYKETTHUC DATE,
	MUCTIEU BIGINT NOT NULL,
	UNGHO BIGINT,
	NOIDUNGCT VARCHAR,
	TRANGTHAICT boolean);

ALTER TABLE CHUONGTRINH ALTER COLUMN UNGHO SET DEFAULT 0;

CREATE TABLE HINHANHCT (
	MAANH INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	MACT INT NOT NULL,
	HINHANH bytea,
	TYPE VARCHAR(20));

ALTER TABLE HINHANHCT ADD CONSTRAINT FK_HINHANHCT_CHUONGTRINH
FOREIGN KEY (MACT) REFERENCES CHUONGTRINH (MACT)
ON UPDATE CASCADE
ON DELETE CASCADE

CREATE TABLE TAIKHOAN (
	MATK INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	EMAIL VARCHAR(100) NOT NULL,
	MATKHAU VARCHAR(25) NOT NULL,
	TEN VARCHAR(50),
	HO VARCHAR(100),
	SODT VARCHAR(15),
	DIACHI VARCHAR(100),
	TRANGTHAI BOOLEAN DEFAULT FALSE,
	XACTHUC BOOLEAN DEFAULT FALSE,
	ROLE VARCHAR(10),
	IMAGE BYTEA,
	IMGTYPE VARCHAR(20),
	KHOAMK VARCHAR(25) NOT NULL)

CREATE TABLE DONGGOP (
	MAHD INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL,
	MACT INT NOT NULL,
	MATK INT NOT NULL,
	SOTIEN BIGINT NOT NULL,
	NGAY DATE NOT NULL,
	TINNHAN VARCHAR(200),
	ANDANH BOOLEAN DEFAULT FALSE)

ALTER TABLE DONGGOP ADD CONSTRAINT FK_DONGGOP_CHUONGTRINH
FOREIGN KEY (MACT) REFERENCES CHUONGTRINH (MACT)
ON UPDATE CASCADE
ON DELETE CASCADE;

ALTER TABLE DONGGOP ADD CONSTRAINT FK_DONGGOP_TAIKHOAN
FOREIGN KEY (MATK) REFERENCES TAIKHOAN (MATK)
ON UPDATE CASCADE
ON DELETE CASCADE;

create view viewchuongtrinhmoinhat
as
select row_number() over (order by ngaybatdau desc, ngayketthuc desc, mact desc) as row,
	mact, tieudect, ngaybatdau, ngayketthuc, muctieu, ungho, trangthaict from
	(select mact, tieudect, ngaybatdau, ngayketthuc, muctieu, ungho, trangthaict from chuongtrinh) as mytable

create view viewchuongtrinhtrangchinh
as
select row_number() over (order by (case when ngayketthuc is null then 0 else 1 end), ngaybatdau desc, mact desc) as row,
	mact, tieudect, ngaybatdau, ngayketthuc, muctieu, ungho, trangthaict from
	(select mact, tieudect, ngaybatdau, ngayketthuc, muctieu, ungho, trangthaict from chuongtrinh where trangthaict = true ) as mytable;

create view viewchuongtrinhtrangchinhall
as
select row_number() over (order by (case when ngayketthuc is null then 0 else 1 end), ngaybatdau desc, mact desc) as row,
	mact, tieudect, ngaybatdau, ngayketthuc, muctieu, ungho, trangthaict from
	(select mact, tieudect, ngaybatdau, ngayketthuc, muctieu, ungho, trangthaict from chuongtrinh) as mytable;

create or replace function trigger_function()
	returns trigger
	language plpgsql
as $$
begin
	update chuongtrinh set ungho = ungho + NEW.SOTIEN
	where chuongtrinh.mact = NEW.mact;
  RETURN NEW;
end;$$;

drop trigger if exists cap_nhat_so_tien_ung_ho on donggop;
create trigger cap_nhat_so_tien_ung_ho
after insert
on donggop
for each row
execute procedure trigger_function();

create or replace function getMoney(listSize integer, eventId integer)
returns setof BIGINT as $$
    declare       
        fr integer;
    begin
        fr := 0;        
        while fr < listSize loop
            return next coalesce(sum(sotien), 0) from donggop where mact = eventId and ngay <= current_date - fr;
            fr := fr + 1;
        end loop;
    end;
$$ language plpgsql;