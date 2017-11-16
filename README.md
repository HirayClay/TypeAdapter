### TypeAdapter
有人问我类似效果图里面那样的界面用RecyclerView怎么做，感觉Adapter乱成一团，
根本没法写下去，所以立马意识到必须得对Adapter里面的东西进行分离，于是有了TypeAdapter

### Shot
<img src="static/GIF.gif" width="411px" height="639px"/>

### Usage

首先确定有哪几种item，比如有 dog、cat 、fish三种
先创建对应的数据类型 DogItem,CatItem,FishItem

并且为这三类item创建对应的TypeBinder

```java
class DogBinder extends TypeBinder<DogItem,DogBinder.ViewHolder>{

        @Override
        protected ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }
    
        @Override
        protected void onBindViewHolder(DogItem data, ViewHolder holder) {
    
        }
    
        public class ViewHolder extends RecyclerView.ViewHolder {
            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
}
```
以此类推

创建TypeAdapter 并且设置数据
```java
    TypeAdapter adapter = new TypeAdpter();
    List datas = new ArrayList<>();
    datas.add(new DogItem());
    datas.add(new FishItem());
    datas.add(new CatItem());
    datas.add(new CatItem());
    datas.add(new CatItem());
    datas.add(new DogItem());
    datas.add(new DogItem());
    adapter.setData(datas);
    adapter.addBinder(new DogBinder());
    adapter.addBinder(new CatBinder());
    adapter.addBinder(new FishBinder());
    
    recyclerView.setAdapter(adapter);
```