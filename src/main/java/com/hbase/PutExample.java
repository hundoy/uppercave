package com.hbase;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * Created by Administrator on 2016/7/19.
 */
public class PutExample {
    public static void main(String[] args) throws Exception{
        Configuration conf = HBaseConfiguration.create();
        HTable table = new HTable(conf, "pet");
        Put put = new Put(Bytes.toBytes("coffee"));
        put.addColumn(Bytes.toBytes("baseinfo"), Bytes.toBytes("color"), Bytes.toBytes("yellow,white"));
        put.addColumn(Bytes.toBytes("baseinfo"), Bytes.toBytes("variety"), Bytes.toBytes("tugou"));
        table.put(put);
    }
}
