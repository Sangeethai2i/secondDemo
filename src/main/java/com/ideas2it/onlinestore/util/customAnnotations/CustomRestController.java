/*
 * Copyright (c) 2022 Ideas2it, Inc.All rights are reserved.
 *
 * This document is protected by copyright. No part of this document may be
 * reproduced in any form by any means without prior written authorization of
 * Ideas2it and its licensors, if any.
 */
package com.ideas2it.onlinestore.util.customAnnotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * This a custom annotation class.
 * 
 * @author Aabid
 * @version 1.0
 * @since 16-12-2022
 *
 */

@Documented
@RestController
@RequestMapping("/api/v1/")
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomRestController {
}