package com.umair.archiTemplate2.di

import javax.inject.Scope

import kotlin.annotation.Retention
import kotlin.annotation.Target
import kotlin.annotation.AnnotationRetention


@Scope
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.TYPE, AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
annotation class FragmentScoped
