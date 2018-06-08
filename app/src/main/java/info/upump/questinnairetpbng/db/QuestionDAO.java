package info.upump.questinnairetpbng.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import net.sqlcipher.DatabaseUtils;

import java.util.ArrayList;
import java.util.List;

import info.upump.questinnairetpbng.entity.Answer;
import info.upump.questinnairetpbng.entity.Interval;
import info.upump.questinnairetpbng.entity.Question;


public class QuestionDAO extends DBDAO {
    private Cursor cursor;
    private static final String WHERE_ID_EQUALS = DataBaseHelper.TABLE_KEY_ID
            + " =?";

    public QuestionDAO(Context context) {
        super(context);
    }

    public long save(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_BODY, question.getBody().toLowerCase());
        if (question.getImg() != null) {
            cv.put(DataBaseHelper.TABLE_KEY_IMG, question.getImg().toLowerCase());
        } else {
            cv.put(DataBaseHelper.TABLE_KEY_IMG, question.getImg());
        }

        cv.put(DataBaseHelper.TABLE_KEY_CATEGORY, question.getCategory().toLowerCase());
        if (question.getComment() != null) {
            cv.put(DataBaseHelper.TABLE_KEY_COMMENT, question.getComment().toLowerCase());
        } else {
            cv.put(DataBaseHelper.TABLE_KEY_COMMENT, question.getComment());
        }

        return database.insert(DataBaseHelper.TABLE_QUESTION, null, cv);
    }

    public long update(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_BODY, question.getBody().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_IMG, question.getImg().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_CATEGORY, question.getCategory().toLowerCase());
        cv.put(DataBaseHelper.TABLE_KEY_COMMENT, question.getComment().toLowerCase());
        return database.update(DataBaseHelper.TABLE_QUESTION, cv,
                WHERE_ID_EQUALS, new String[]{String.valueOf(question.getId())});

    }

    public int delete(Question question) {
        return database.delete(DataBaseHelper.TABLE_QUESTION, WHERE_ID_EQUALS, new String[]{question.getId() + ""});
    }

    public List<Question> getQuestions() {
        Cursor cursor = null;
        List<Question> questions = new ArrayList<>();
        try {
            cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                    new String[]{
                            DataBaseHelper.TABLE_KEY_ID,
                            DataBaseHelper.TABLE_KEY_BODY,
                            DataBaseHelper.TABLE_KEY_CATEGORY,
                            DataBaseHelper.TABLE_KEY_IMG,
                            DataBaseHelper.TABLE_KEY_COMMENT},
                    null, null, null, null, null
            );
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setBody(stringToUpperCase(cursor.getString(1)));
                    question.setCategory(stringToUpperCase(cursor.getString(2)));
                    question.setImg(cursor.getString(3));
                    question.setComment(stringToUpperCase(cursor.getString(4)));
                    questions.add(question);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        AnswerDAO answerDAO = new AnswerDAO(context);
        for (Question q : questions) {
            List<Answer> answers = answerDAO.getAnswerByParentId(q.getId());
            q.getAnswers().addAll(answers);
        }

        return questions;
    }

    public int getCount(String category) {
         String databaseCompare;
        if(category == null){
            databaseCompare = "select count(*) from "+DataBaseHelper.TABLE_QUESTION;
        }else databaseCompare = "select count(*) from "+DataBaseHelper.TABLE_QUESTION+" where " +DataBaseHelper.TABLE_KEY_CATEGORY +" = "+category;
        int l = (int) DatabaseUtils.longForQuery(database, databaseCompare,null);
        System.out.println(l);
        return l;
    }

    public Cursor getCursorQuestion() {
        cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_CATEGORY,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COMMENT},
                null, null, null, null, null, null
        );
        return cursor;
    }

    public Cursor searchByString(String search) {
        cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_CATEGORY,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COMMENT},
                DataBaseHelper.TABLE_KEY_BODY + " LIKE ? ", new String[]{String.valueOf("%" + search.toLowerCase() + "%")}, null, null, null, null
        );
        return cursor;
    }


    public Cursor getSearchInCategory(String category) {
        cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_BODY,
                        DataBaseHelper.TABLE_KEY_CATEGORY,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COMMENT},
                DataBaseHelper.TABLE_KEY_CATEGORY + " LIKE ? ", new String[]{String.valueOf("%" + category.toLowerCase() + "%")}, null, null, null, null
        );
        return cursor;

    }

    private String stringToUpperCase(String s) {
        return s != null && s.length() != 0 ? s.substring(0, 1).toUpperCase() + s.substring(1) : null;
    }

    public List<Question> getQuestionsInterval(Interval interval) {
        Cursor cursor = null;
        List<Question> questions = new ArrayList<>();
        try {
            if (interval.getCategory() == null) {
                cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                        new String[]{
                                DataBaseHelper.TABLE_KEY_ID,
                                DataBaseHelper.TABLE_KEY_BODY,
                                DataBaseHelper.TABLE_KEY_CATEGORY,
                                DataBaseHelper.TABLE_KEY_IMG,
                                DataBaseHelper.TABLE_KEY_COMMENT},
                        "LIMIT ? offset ? ", new String[]{
                                String.valueOf(interval.getFinish() - interval.getStart()),
                                String.valueOf(interval.getStart() - 1)},
                        null, null, null, null
                );
            } else {

                cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                        new String[]{
                                DataBaseHelper.TABLE_KEY_ID,
                                DataBaseHelper.TABLE_KEY_BODY,
                                DataBaseHelper.TABLE_KEY_CATEGORY,
                                DataBaseHelper.TABLE_KEY_IMG,
                                DataBaseHelper.TABLE_KEY_COMMENT},
                        DataBaseHelper.TABLE_KEY_CATEGORY + " LIKE ? LIMIT ? offset ? ", new String[]{String.valueOf("%" + interval.getCategory().toLowerCase() + "%"),
                                String.valueOf(interval.getFinish() - interval.getStart()),
                                String.valueOf(interval.getStart() - 1)},
                        null, null, null, null
                );
            }
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setBody(stringToUpperCase(cursor.getString(1)));
                    question.setCategory(stringToUpperCase(cursor.getString(2)));
                    question.setImg(cursor.getString(3));
                    question.setComment(stringToUpperCase(cursor.getString(4)));
                    questions.add(question);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        AnswerDAO answerDAO = new AnswerDAO(context);
        for (Question q : questions) {
            List<Answer> answers = answerDAO.getAnswerByParentId(q.getId());
            q.getAnswers().addAll(answers);
        }

        return questions;

    }

    public List<Question> getQuestionsByPartId(int partId) {
        Cursor cursor = null;
        List<Question> questions = new ArrayList<>();
        try {
            cursor = database.query(DataBaseHelper.TABLE_QUESTION,
                    new String[]{
                            DataBaseHelper.TABLE_KEY_ID,
                            DataBaseHelper.TABLE_KEY_BODY,
                            DataBaseHelper.TABLE_KEY_CATEGORY,
                            DataBaseHelper.TABLE_KEY_IMG,
                            DataBaseHelper.TABLE_KEY_COMMENT},
                    DataBaseHelper.TABLE_KEY_CATEGORY + " = ? ", new String[]{String.valueOf(partId)},


                    null, null, null, null
            );
            if (cursor.moveToFirst()) {
                do {
                    Question question = new Question();
                    question.setId(cursor.getInt(0));
                    question.setBody(stringToUpperCase(cursor.getString(1)));
                    question.setCategory(stringToUpperCase(cursor.getString(2)));
                    question.setImg(cursor.getString(3));
                    question.setComment(stringToUpperCase(cursor.getString(4)));
                    questions.add(question);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        AnswerDAO answerDAO = new AnswerDAO(context);
        for (Question q : questions) {
            List<Answer> answers = answerDAO.getAnswerByParentId(q.getId());
            q.getAnswers().addAll(answers);
        }

        return questions;
    }
}
