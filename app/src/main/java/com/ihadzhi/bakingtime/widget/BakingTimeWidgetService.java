package com.ihadzhi.bakingtime.widget;

/*
* Copyright (C) 2017 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*  	http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import com.ihadzhi.bakingtime.R;
import com.ihadzhi.bakingtime.model.Recipe;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 */
public class BakingTimeWidgetService extends IntentService {

    public static final String ACTION_UPDATE_INGREDIENTS = "com.ihadzhi.bakingtime.action.update_ingredients_widgets";
    public static final String EXTRA_RECIPE = "com.ihadzhi.bakingtime.extra.RECIPE";;

    public BakingTimeWidgetService() {
        super("BakingTimeWidgetService");
    }

    public static void startActionIngredientsUpdate(Context context, Recipe recipe) {
        Intent intent = new Intent(context, BakingTimeWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startService(intent);
    }

    public static void startActionUpdateIngredients(Context context) {
        Intent intent = new Intent(context, BakingTimeWidgetService.class);
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        context.startService(intent);
    }

    /**
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_INGREDIENTS.equals(action)) {
                handleActionUpdateIngredients(intent.getParcelableExtra(EXTRA_RECIPE));
            }
        }
    }

    private void handleActionUpdateIngredients(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingTimeWidgetProvider.class));
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
        BakingTimeWidgetProvider.updateIngredientsWidgets(this, appWidgetManager, recipe, appWidgetIds);
    }
}
