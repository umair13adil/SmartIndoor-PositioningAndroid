package com.umair.archiTemplate2.di


import java.lang.annotation.Documented
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

import javax.inject.Scope


@Documented
@Scope
@Retention(RetentionPolicy.RUNTIME)
annotation class ActivityScoped
