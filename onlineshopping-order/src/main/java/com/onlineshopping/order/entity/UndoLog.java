package com.onlineshopping.order.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UndoLog implements Serializable {
    private Long id;

    private Long branchId;

    private String xid;

    private String context;

    private byte[] rollbackInfo;

    private Integer logStatus;

    private Date logCreated;

    private Date logModified;

    private String ext;

    private static final long serialVersionUID = 1L;
}