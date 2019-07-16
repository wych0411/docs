Hive建表方式共有三种：
- 直接建表法
- 查询建表法
- like建表法

***************************************************************************************************************
* CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name                                    *
*   [(col_name data_type [COMMENT col_comment],...[constraint_specification])]                                *
*   [COMMENT table_comment]                                                                                   *
*   [PARTITIONED BY (col_name data_type [COMMENT col_comment], ...)]                                          *
*   [CLUSTERED BY (col_name, col_name, ...) [SORTED BY (col_name [ASC|DESC], ...)] INTO num_buckets BUCKETS]  *
*   [SKEWED BY (col_name, col_name, ...)]                                                                     *
*     ON ((col_value, col_value, ...), (col_value, col_value, ...), ...)                                      *
*     [STORED AS DIRECTORIES]                                                                                 *
*   [                                                                                                         *
*   	[ROW FORMAT row_format]                                                                               *
*   	[STORED AS file_format]                                                                               *
*   	  | STORED BY 'storage.handler.class.name' [WITH SERDEPROPERTIES(...)]                                *
*   ]                                                                                                         *
*   [LOCATION hdfs_path]                                                                                      *
*   [TBLPROPERTIES (property_name=property_value, ...)]                                                       *
*                                                                                                             *
* [AS select_statement];                                                                                      *
**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**

***************************************************************************************************************
* CREATE [TEMPORARY] [EXTERNAL] TABLE [IF NOT EXISTS] [db_name.]table_name                                    *
*   LIKE existing_table_or_view_name                                                                          *
* [LOCATION hdfs_path];                                                                                       *
**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**


external

- 未被external修饰的是内部表( managed table )，被external修饰的为外部表( external table );
  内部表数据由Hive自身管理，外部表数据由HDFS管理；
  内部表数据存储的位置是hive.metastore.warehouse.dir( 默认: /user/hive/warehouse )，外部表数据的存储位置由自己指定；
  删除内部表会直接删除元数据( metadata )及存储数据；删除外部表仅仅会删除元数据，HDFS上的文件并不会被删除；
  对内部表的修改会将修改直接同步给元数据，而对外部表结构和分区进行修改，则需要修复（ MSCK REPAIR TABLE table_name; ）

A table created without the EXTERNAL clause is called a managed table because Hive manages its data.

Managed and External Tables
By default Hive creates managed tables, where files, metadata and statistics are managed by internal Hive processes. A managed table is stored under the hive.metastore.warehouse.dir path property, by default in a folder path similar to /apps/hive/warehouse/databasename.db/tablename/. The default location can be overriden by the location property during table creation. If a managed table or partition is dropped, the data and metadata associated with that table or partition are deleted. If the PURGE option is not specified, the data is moved to a trash folder for a defined duration.
Use managed tables when Hive should manage the lifecycle of the table, or when generating temporary tables.
An external table describes the metadata / schema on external files. External table files can be accessed and managed by processes outside of Hive. External tables can access data stored in sources such as Azure Storage Volumes (ASV) or remote HDFS locations. If the structure or partitioning of an external table is changed, an MSCK REPAIR TABLE table_name statement can be used to refresh metadata information.
Use external tables when files are already present or in remote locations, and the files should remain even if the table is dropped.
Managed or external tables can be identified using the DESCRIBE FORMATTED table_name command, which will display either MANAGED_TABLE or EXTERNAL_TABLE depending on table type.
Statistics can be managed on internal and external tables and partitions for query optimization. 


temporary

A temporary table is a convenient way for an application to automatically manage intermediate data generated during a complex query. Rather than manually deleting tables needed only as temporary data in a complex query, Hive automatically deletes all temporary tables at the end of the Hive session in which they are created.
The data in these tables is stored in the user's scratch directory rather than in the Hive warehouse directory. The scratch directory effectively acts as the user' data sandbox, located by default in /tmp/hive-<username>.
Hive users create temporary tables using the 'TEMPORARY' keyword
Multiple Hive users can create multiple Hive temporary tables with the same name because each table resides in a separate session.
Temporary tables support most table options, but not all. The following features are not supported:
- Partition columns
- Indexes
A temporary table with the same name as a permanent table will cause all references to that table name to resolve to the temporary talbe. The user cannot access the permanent table during that session without dropping or renaming the temporary table.


data_type
  :primitive_type
  | array_type
  | map_type
  | struct_type
  | union_type


primitive_type
  : TINYINT
  | SMALLINT
  | INT
  | BIGINT
  | BOOLEAN
  | FLOAT
  | DOUBLE
  | DOUBLE PRECISION
  | STRING
  | BINARY
  | TIMESTAMP
  | DECIMAL
  | DECIMAL(precision, scale)
  | DATE
  | VARCHAR
  | CHAR



array_type
  : ARRAY<data_type>



map_type
  : MAP<primitive_type, data_type>

struct_type
  :STRUCT<col_name:data_type [COMMENT col_comment], ...>


union_type
  : UNIONTYPE<data_type, data_type, ...>


constraint_specification:
  : [, PRIMARY KEY (col_name, ...) DISABLE NOVALIDATE]
    [, CONSTRAINT constraint_name FOREIGN KEY (col_name, ...) REFERENCES table_name(col_name, ...) DISABLE NOVALIDATE ]