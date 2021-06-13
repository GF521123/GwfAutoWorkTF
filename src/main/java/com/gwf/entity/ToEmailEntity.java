package com.gwf.entity;

import java.io.Serializable;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class ToEmailEntity implements Serializable {

	/**
	 * 邮件接收方，可多人
	 */
	private String[] tos;
	/**
	 * 邮件主题
	 */
	private String subject;
	/**
	 * 邮件内容
	 */
	private String content;
}
