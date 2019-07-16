row format

*******************************************************************************************************************
* row_format                                                                                                      *
*   :DELIMITED [FIELDS TERMINATED BY char [ESCAPED BY char]] [COLLECTION ITEMS TERMINATED BY char]                *
*     [MAP KEYS TERMINATED BY char] [LINES TERMINATED BY char]                                                    *
*     [NULL DEFINED AS char]                                                                                      *
*   | SERDE serde_name [WITH SERDEPROPERTIES (property_name=property_value, property_name=property_value, ...)]   *
**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**

Hive将HDFS上的文件映射成表结构，通过分隔符来区分列（比如',' ';' or '^'等），row_format就是用于指定序列化和反序列化的规则。
- FIELDS TERMINATED BY char 
  用于指定列分隔符
- COLLECTION ITEMS TERMINATED BY char
  用于指定数组类型列取值中的分隔符
- MAP KEYS TERMINATED BY char
  用于指定键值对类型列取值中的分隔符
- LINES TERMINATED BY char
  用于区分行数据，默认是换行符



file format

********************************************************************************************************************
* file_format:                                                                                                     *
*   : SEQUENCEFILE                                                                                                 *
*   | TEXTFILE                                                                                                     *
*   | RCFILE                                                                                                       *
*   | ORC                                                                                                          *
*   | PARQUET                                                                                                      *
*   | AVRO                                                                                                         *
*   | INPUTFORMAT input_format_classname OUTPUTFORMAT output_format_classname                                      *
**~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~**

默认TEXTFILE, 即文本格式，可以直接打开。