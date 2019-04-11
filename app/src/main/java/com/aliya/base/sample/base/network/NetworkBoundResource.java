package com.aliya.base.sample.base.network;

import android.arch.lifecycle.LiveData;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

/**
 * NetworkBoundResource
 *
 * @param <ResultType>  Type for the Resource data.
 * @param <RequestType> Type for the API response.
 * @author a_liYa
 * @date 2019/3/16 07:54.
 */
public abstract class NetworkBoundResource<ResultType, RequestType> {

    // Called to save the result of the API response into the database.
    @WorkerThread
    protected abstract void saveCallResult(@NonNull RequestType item);

    // Called with the data in the database to decide whether to fetch
    // potentially updated data from the network.
    @MainThread
    protected abstract boolean shouldFetch(@Nullable ResultType data);

    // Called to get the cached data from the database.
    @NonNull
    @MainThread
    protected abstract LiveData<ResultType> loadFromDb();

    // Called to create the API call.
    @NonNull
    @MainThread
    protected abstract LiveData<RequestType> createCall();

    // Called when the fetch fails. The child class may want to reset components
    // like rate limiter.
//    @MainThread
//    protected void onFetchFailed();

    // Returns a LiveData object that represents the resource that's implemented in the base class.
//    public final LiveData<Resource<ResultType>> getAsLiveData();

}
