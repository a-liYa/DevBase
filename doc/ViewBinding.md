
```
布局
R.layout.activity_main
```
```
Activity 使用示例. 

ActivityMainBinding mBinding;
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	mBinding = ActivityMainBinding.inflate(getLayoutInflater());
      setContentView(mBinding.getRoot());
}
```
```
Fragment 使用示例.

ActivityMainBinding mBinding;
public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
	mBinding = ActivityMainBinding.inflate(inflater, container, false);
	return mBinding.getRoot();
}
```

```
ViewHolder 使用示例.

ActivityMainBinding mViewBinding;
public ViewHolder(@NonNull ViewGroup parent, AreaSelectViewModel selected) {
	super(parent, R.layout.activity_main);
	mViewBinding = ActivityMainBinding.bind(itemView);
}
```

```
<include
	android:id="@+id/title"
	layout="@layout/title_layout"
	...
	/>
 
 使用
 mBinding.title.ids
 
 分离include的布局
 mTitleLayoutBinding = TitleLayoutBinding.bind(mBinding.getRoot());
```
