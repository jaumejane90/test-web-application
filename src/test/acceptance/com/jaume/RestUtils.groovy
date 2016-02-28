package com.jaume

import okhttp3.*

class RestUtils {

    def static getUrl(String url) {
        OkHttpClient client = new OkHttpClient()

        Request request = new Request.Builder()
                .url(url)
                .build()

        Response response = client.newCall(request).execute()
        return response.body().string()
    }

    def static boolean authenticate(String url, String userName, String password) {
        OkHttpClient client = new OkHttpClient.Builder()
                .authenticator(new Authenticator() {

            @Override
            public Request authenticate(Route route, Response response) throws IOException {
                String credential = Credentials.basic(userName, password);
                return response.request().newBuilder().header("Authorization", credential).build();
            }
        }).build();

        Request request = new Request.Builder().url(url).build();
        Response response = client.newCall(request).execute();
        return response.isSuccessful();
    }


}
