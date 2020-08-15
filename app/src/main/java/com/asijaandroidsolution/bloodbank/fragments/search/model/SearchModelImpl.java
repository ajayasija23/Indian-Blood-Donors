package com.asijaandroidsolution.bloodbank.fragments.search.model;

import com.asijaandroidsolution.bloodbank.fragments.search.presenter.SearchPresenterImpl;

public class SearchModelImpl implements SearchModel {
    private SearchPresenterImpl searchPresenter;
    public SearchModelImpl(SearchPresenterImpl searchPresenter) {
        this.searchPresenter=searchPresenter;
    }

    @Override
    public void performQuery() {

    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onError() {

    }
}
