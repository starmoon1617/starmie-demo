/*
 * Copyright (c) 2023, Starmoon1617 and/or Nathan Liao. All rights reserved.
 *
 */
package io.github.starmoon1617.starmie.demo.biz.manager;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;

import org.junit.jupiter.api.Test;

import io.github.starmoon1617.starmie.demo.biz.model.Attachment;
import io.github.starmoon1617.starmie.demo.test.base.BaseTest;
import jakarta.annotation.Resource;

/**
 * 
 * 
 * @date 2023-11-22
 * @author Nathan Liao
 */
public class AttachmentManagerTest extends BaseTest {

    @Resource
    private AttachmentManager attachmentManager;

    @Test
    public void test_add_attach() throws Exception {
        FileInputStream fio = new FileInputStream("D:/ico/guest.ico");

        Attachment attachment = new Attachment();
        
        attachment.setType(1);
        attachment.setRemark("test");
        attachment.setStatus(1);
        
        attachment.setCreateBy("admin");
        attachment.setCreateTime(new Date());
        attachment.setUpdateBy("admin");
        attachment.setUpdateTime(new Date());
        
        FileChannel fc = fio.getChannel();
        ByteArrayOutputStream baos = new ByteArrayOutputStream((int) fc.size());
        ByteBuffer bb = ByteBuffer.allocate(1024 * 4);
        int size = 0;
        for (;;) {
            size = fc.read(bb);
            bb.flip();
            if (size <= 0) {
                break;
            }
            baos.write(bb.array(), 0 ,size);
            bb.clear();
        }
        attachment.setContent(baos.toByteArray());
        
        attachmentManager.save(attachment);
    }
    
    @Test
    public void test_get_attach() throws Exception {
        Attachment param = new Attachment();
        param.setId(11L);
        
        Attachment attachment = attachmentManager.find(param);
        if (attachment != null) {
            FileOutputStream fos = new FileOutputStream("D:/ico/guest-1.ico");
            fos.write(attachment.getContent());
            fos.flush();
            fos.close();
        }
    }
}
