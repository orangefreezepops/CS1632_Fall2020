TARGETS = heap stack stack_overflow stack_pointer_return datarace binary_tree
OBJS = heap.o stack.o stack_overflow.o o stack_pointer_return.o datarace.o binary_tree.o
CC = /afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang
COPT = -g -w
LOPT =

ASANTARGETS = stack_overflow.asan stack_pointer_return.asan binary_tree.asan
ASANOBJS = stack_overflow.asan.o stack_pointer_return.asan.o binary_tree.asan.o
ASANCOPT = -fsanitize=address
ASANLOPT = -fsanitize=address

TSANTARGETS = datarace.tsan
TSANOBJS = datarace.tsan.o
TSANCOPT = -fPIE -pthread -fsanitize=thread
TSANLOPT = -pie -pthread -fsanitize=thread

all: $(TARGETS) $(ASANTARGETS) $(TSANTARGETS)

%.o: %.c
	$(CC) -c $(COPT) $< -o $@

%.asan.o: %.c
	$(CC) -c $(COPT) $(ASANCOPT) $< -o $@

%.tsan.o: %.c
	$(CC) -c $(COPT) $(TSANCOPT) $< -o $@

heap: heap.o
	$(CC) $< $(LOPT) -o $@

stack: stack.o
	$(CC) $< $(LOPT) -o $@

stack_overflow: stack_overflow.o
	$(CC) $< $(LOPT) -o $@

stack_pointer_return: stack_pointer_return.o
	$(CC) $< $(LOPT) -o $@

datarace: datarace.o
	$(CC) $< $(LOPT) -pthread -o $@

binary_tree: binary_tree.o
	$(CC) $< $(LOPT) -lapr-1 -o $@

stack_overflow.asan: stack_overflow.asan.o
	$(CC) $< $(LOPT) $(ASANLOPT) -o $@

stack_pointer_return.asan: stack_pointer_return.asan.o
	$(CC) $< $(LOPT) $(ASANLOPT) -o $@

binary_tree.asan: binary_tree.asan.o
	$(CC) $< $(LOPT) $(ASANLOPT) -lapr-1 -o $@

datarace.tsan: datarace.tsan.o
	$(CC) $< $(LOPT) $(TSANLOPT) -o $@

clean:
	rm -f $(TARGETS) $(ASANTARGETS) $(TSANTARGETS) $(OBJS) $(ASANOBJS) $(TSANOBJS)
