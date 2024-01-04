package com.onlineshopping.common.constant;

public class ProductConstant {
    public enum AttrEnum{
        ATTR_TYPE_BASE(1,"base"),
        //sale：销售属性
        ATTR_TYPE_SALE(0,"sale");
        private int code;
        private String msg;

        AttrEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }


        public String getMsg() {
            return msg;
        }

    }

    public enum StatusEnum{
        NEW_SPU(0,"新建商品"),
        SPU_UP(1,"商品上架"),
        SPU_DOWN(-1,"商品下架");
        private int code;
        private String msg;

        StatusEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }

    }
}
