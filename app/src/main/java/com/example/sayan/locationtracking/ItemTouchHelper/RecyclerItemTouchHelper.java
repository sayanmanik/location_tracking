package com.example.sayan.locationtracking.ItemTouchHelper;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.sayan.locationtracking.Adapter.FragmentAdapter;

/**
 * Created by 1605476 and 11-Oct-18
 **/
public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    /**
     * Creates a Callback for the given drag and swipe allowance. These values serve as
     * defaults
     * and if you want to customize behavior per ViewHolder, you can override
     * {@link //getSwipeDirs(RecyclerView, ViewHolder)}
     * and / or {@link //getDragDirs(RecyclerView, ViewHolder)}.
     *
     * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
     *                  composed of {@link //LEFT}, {@link //RIGHT}, {@link //START}, {@link
     *                  //END},
     *                  {@link //UP} and {@link //DOWN}.
     * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
     *                  composed of {@link //LEFT}, {@link //RIGHT}, {@link //START}, {@link
     *                  //END},
     *                  {@link //UP} and {@link //DOWN}.
     */

    public RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs,RecyclerItemTouchHelperListener listener)
    {
        super(dragDirs, swipeDirs);
        this.listener=listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target)
    {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState)
    {
        if(viewHolder!=null)
        {
            final View foreGroundView=((FragmentAdapter.MyViewHolder)viewHolder).getForeGround();
            getDefaultUIUtil().onSelected(foreGroundView);
        }
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        final View foreGroundView=((FragmentAdapter.MyViewHolder)viewHolder).getForeGround();
        getDefaultUIUtil().onDraw(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder)
    {
        final View foregroundView = ((FragmentAdapter.MyViewHolder) viewHolder).getForeGround();
        getDefaultUIUtil().clearView(foregroundView);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive)
    {
        final View foreGroundView=((FragmentAdapter.MyViewHolder)viewHolder).getForeGround();
        getDefaultUIUtil().onDrawOver(c,recyclerView,foreGroundView,dX,dY,actionState,isCurrentlyActive);
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction)
    {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection)
    {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener
    {
        void onSwiped(RecyclerView.ViewHolder viewHolder,int direction, int position);
    }
}