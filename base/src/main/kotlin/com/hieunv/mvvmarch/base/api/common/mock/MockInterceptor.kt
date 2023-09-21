package com.hieunv.mvvmarch.base.api.common.mock

import android.content.Context
import com.hieunv.mvvmarch.base.BuildConfig
import com.hieunv.mvvmarch.base.api.common.mock.IOUtils.getJsonStringFromFile
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.net.HttpURLConnection.HTTP_OK

class MockInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.DEBUG) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.endsWith(MOCK_REQUEST_LOGIN) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_LOGIN_SUCCESS)
                uri.endsWith(MOCK_REQUEST_LOGOUT) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_LOGOUT_SUCCESS)

                uri.endsWith(MOCK_REQUEST_GET_POSTS_PAGE_1) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_1)
                uri.endsWith(MOCK_REQUEST_GET_POSTS_PAGE_2) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_2)
                uri.endsWith(MOCK_REQUEST_GET_POSTS_PAGE_3) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_3)
                uri.endsWith(MOCK_REQUEST_GET_POSTS_PAGE_4) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_4)
                uri.endsWith(MOCK_REQUEST_GET_POSTS_PAGE_EMPTY) -> getJsonStringFromFile(context.assets, MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_EMPTY)
                else -> ""
            }

            return if (responseString.isNotEmpty()) {
                chain.proceed(chain.request())
                        .newBuilder()
                        .code(HTTP_OK)
                        .protocol(Protocol.HTTP_2)
                        .message("OK")
                        .body(
                                responseString.toByteArray()
                                        .toResponseBody("application/json".toMediaTypeOrNull())
                        )
                        .addHeader("content-type", "application/json")
                        .build()
            } else {
                chain.proceed(chain.request())
            }
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                    "MockInterceptor is only meant for Testing Purposes and " +
                            "bound to be used only with DEBUG mode"
            )
        }
    }

    companion object {
        // Mock Auth Api
        const val MOCK_REQUEST_LOGIN = "login.seam"
        const val MOCK_REQUEST_LOGOUT = "logout.seam"

        const val MOCK_RESPONSE_LOGIN_SUCCESS = "mock/api/auth/login_success.json"
        const val MOCK_RESPONSE_LOGOUT_SUCCESS = "mock/api/auth/logout_success.json"

        // Mock Posts Paging
        const val MOCK_REQUEST_GET_POSTS_PAGE_1 = "get_posts.seam?limit=10&after_id=-1"
        const val MOCK_REQUEST_GET_POSTS_PAGE_2 = "get_posts.seam?limit=10&after_id=10"
        const val MOCK_REQUEST_GET_POSTS_PAGE_3 = "get_posts.seam?limit=10&after_id=20"
        const val MOCK_REQUEST_GET_POSTS_PAGE_4 = "get_posts.seam?limit=10&after_id=30"
        const val MOCK_REQUEST_GET_POSTS_PAGE_EMPTY = "get_posts.seam?limit=10&after_id=36"

        const val MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_1 = "mock/api/post/get_posts_paging/get_posts_page_1.json"
        const val MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_2 = "mock/api/post/get_posts_paging/get_posts_page_2.json"
        const val MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_3 = "mock/api/post/get_posts_paging/get_posts_page_3.json"
        const val MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_4 = "mock/api/post/get_posts_paging/get_posts_page_4.json"
        const val MOCK_RESPONSE_FILE_NAME_GET_POSTS_PAGE_EMPTY = "mock/api/post/get_posts_paging/get_posts_page_empty.json"
    }

}
