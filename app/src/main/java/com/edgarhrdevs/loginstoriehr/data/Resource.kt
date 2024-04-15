package com.edgarhrdevs.loginstoriehr.data

sealed class Resource <out R>{
    data class Success<out R>(val result: R): Resource<R>()
    data class Failures(val exception: Exception): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}