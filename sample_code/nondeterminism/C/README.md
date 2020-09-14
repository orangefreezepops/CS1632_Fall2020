## Description

This set of code demonstrates concepts we learned in the Software QA and
Nondeterminism lecture.  By trying out these programs, you will learn the following:

1. Observe how values of pointers in C are randomized through ASLR leading to nondeterministic program behavior.

1. Observe how pointer values can leak out to program output through memory errors.

1. Learn how to turn ASLR off to make C pointers deterministic.

1. Learn how to use ASAN (Google Address Sanitizer) to debug stack overflow memory errors.

1. Learn how to use ASAN (Google Address Sanitizer) to debug dangling pointer memory errors.

1. Observe how dataraces in C leads to nondeterministic program behavior.

1. Learn how to use TSAN (Google Thread Sanitizer) to debug datarace errors.

1. Compare ASAN with Valgrind, another memory error detection tool.

## Connecting to linux.cs.pitt.edu

In order to use ASAN or TSAN, you need to clang version >= 3.1 or gcc version >= 4.8.
Since you are unlikely to have either installed on your local
computer, I will ask you to connect to one of the departmental public Linux
servers through linux.cs.pitt.edu.

If you use Windows, please follow these steps:

1. If you haven't already, install Pulse VPN Desktop Client.  Instructions on how to download and use:
https://www.technology.pitt.edu/services/pittnet-vpn-pulse-secure
Then, set up the VPN client and connect to Pitt VPN  as follows:
https://www.technology.pitt.edu/help-desk/how-to-documents/pittnet-vpn-pulse-secure-connect-pulse-secure-client

1. If you don't have an SSH client, download Putty, a free open source terminal:
https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html
Connect to "linux.cs.pitt.edu" by typing that in the "Host Name" box.  Make sure that port is 22 and SSH is selected in the radio button options.

1. Once connected, the host will ask for your Pitt SSO credentials.  Enter your ID and password.

If you use MacOS or Linux, you already have a terminal in your OS, so you can skip Step 2.  You can simply type on the terminal:
```
ssh USERNAME@linux.cs.pitt.edu
```
Where USERNAME is your Pitt ID.

## Building

Once you are connected to linux.cs.pitt.edu, you first need to copy over the
source code to your local directory.  Go to a directory of your choice (or you
can stay at your default home directory) and do the following:

```
$ cp -R /afs/cs.pitt.edu/courses/1632/nondeterminism/ ./
$ cd nondeterminism/C
```

I have provided a Makefile build script to automate the build.  All you have to do is invoke 'make':

```
$ make
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w heap.c -o heap.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang heap.o  -o heap
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w stack.c -o stack.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack.o  -o stack
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w stack_overflow.c -o stack_overflow.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack_overflow.o  -o stack_overflow
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w stack_pointer_return.c -o stack_pointer_return.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack_pointer_return.o  -o stack_pointer_return
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w datarace.c -o datarace.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang datarace.o  -pthread -o datarace
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w binary_tree.c -o binary_tree.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang binary_tree.o  -lapr-1 -o binary_tree
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w -fsanitize=address stack_overflow.c -o stack_overflow.asan.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack_overflow.asan.o  -fsanitize=address -o stack_overflow.asan
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w -fsanitize=address stack_pointer_return.c -o stack_pointer_return.asan.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack_pointer_return.asan.o  -fsanitize=address -o stack_pointer_return.asan
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w -fsanitize=address binary_tree.c -o binary_tree.asan.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang binary_tree.asan.o  -fsanitize=address -lapr-1 -o binary_tree.asan
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w -fPIE -pthread -fsanitize=thread datarace.c -o datarace.tsan.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang datarace.tsan.o  -pie -pthread -fsanitize=thread -o datarace.tsan
```

Note how when I create ASAN instrumented binaries (e.g. stack_overflow.asan,
stack_pointer_return.asan, ...), I pass the -fsanitize=address compiler option
to clang.  You need to pass it to both the compilation stage and the linking
stage.  The same goes for -fsanitize=thread for TSAN instrumented binaries.

## Testing ASLR (Address Space Layout Randomization)

heap.c is a simple program that mallocs some bytes on the heap and prints out
the pointer to that heap location.  You can use 'nano' to view the file on the
terminal:

```
$ nano heap.c
```

Or, you can view it on the GitHub.  As we learned, even this simple program can
display nondeterministic behavior due to ASLR.  Try it out yourself!

```
$ ./heap
p = 0x1ac0010
$ ./heap
p = 0x23e1010
$ ./heap
p = 0x2428010
```

Your actual values will vary but you can see how the output is randomized.

Likewise, stack.c is a simple program that prints out the pointer to a stack
location.  And it also displays nondeterministic behavior due to ASLR:

```
$ ./stack
p = 0x7ffe4b81a658
$ ./stack
p = 0x7fffa0be7c38
$ ./stack
p = 0x7ffe93f7b188
```

Now, let's try running both with ASLR turned off.  I've written a simple script
named 'run_aslr_off.sh' that does exactly that:

```
$ ./run_aslr_off.sh ./heap
setarch x86_64 -R ./heap
p = 0x602010
$ ./run_aslr_off.sh ./heap
setarch x86_64 -R ./heap
p = 0x602010
$ ./run_aslr_off.sh ./heap
setarch x86_64 -R ./heap
p = 0x602010
$ ./run_aslr_off.sh ./stack
setarch x86_64 -R ./stack
p = 0x7fffffffde98
$ ./run_aslr_off.sh ./stack
setarch x86_64 -R ./stack
p = 0x7fffffffde98
$ ./run_aslr_off.sh ./stack
setarch x86_64 -R ./stack
p = 0x7fffffffde98
```

Note that now the output is no longer random!  This is what it says if you 'man
setarch':

```
$ man setarch
...
 -R, --addr-no-randomize
              Disables randomization of the virtual address space (turns on ADDR_NO_RANDOMIZE).
...
```

Did you ever get the feeling that your C program that used to behave randomly
suddenly becomes deterministic when you run it on top of GDB (GNU Debugger)?
That is because GDB by default turns off ASLR for debugging purposes so that
behavior is reproducible.  Turning off ASLR can be very useful in a debug
setting.

But your end users will most likely have ASLR turned on in their machines for
security.  What then?  Your programs will again be nondeterministic and testing
would no longer guarantee correct behavior.  So we may still get surprise
defects.

How can we have a deterministic program when all addresses are randomized?
Easy: just don't let addresses leak out to program output!  As we discussed,
unless for debugging purposes, programs will almost never intentionally output
addresses where data is stored --- they will typically output the data.  It is
just that addresses leak out to output due to memory errors.  So if we can
catch all memory errors, then problem solved!  ASAN is exactly the kind of tool
that can help you do that.

## Using Google ASAN (Address Sanitizer)

Before starting, we need to setup some OS environment variables used by Google sanitizer:

```
$ source setup_sanitizer_env.sh
```

stack_overflow.c is a buggy program that demonstrates the stack buffer overflow
issue that we discussed in the lecture.  It tries to send bytes beyond the
bounds of an array and ends up sending a pointer value along with the array
data.  It displays nondeterministic behavior due to the pointer value leaking out:

```
$ ./stack_overflow
p = 0x7ffd708463e8
 0  0  0  0  0  0  0  0 e8 63 84 70 fd 7f  0  0
$ ./stack_overflow
p = 0x7ffdaf83c578
 0  0  0  0  0  0  0  0 78 c5 83 af fd 7f  0  0
$ ./stack_overflow
p = 0x7ffd9cabc3f8
 0  0  0  0  0  0  0  0 f8 c3 ab 9c fd 7f  0  0
```

You can see how the send_data function sends out the (randomized) pointer along
with the data.  I also print out the value of pointer p explicitly, just to
show you that the latter half of send_data is sending out the value of p.

Now let's see if ASAN can find the bug for us by running the instrumented binary:

```
$ ./stack_overflow.asan
p = 0x7f5148563020
=================================================================
==54955==ERROR: AddressSanitizer: stack-buffer-overflow on address 0x7f5148563028 at pc 0x0000004f843d bp 0x7ffda7d9e3c0 sp 0x7ffda7d9e3b8
READ of size 1 at 0x7f5148563028 thread T0
    #0 0x4f843c in send_data /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_overflow.c:7:20
    #1 0x4f85a1 in main /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_overflow.c:17:3
    #2 0x7f514bbd73d4 in __libc_start_main (/lib64/libc.so.6+0x223d4)
    #3 0x41b6c9 in _start (/afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_overflow.asan+0x41b6c9)

Address 0x7f5148563028 is located in stack of thread T0 at offset 40 in frame
    #0 0x4f848f in main /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_overflow.c:12
...
```

ASAN is able to pinpoint exactly where the illegal "READ of size 1" happened at
stack_overflow.c:7:20!  That is where the out of bounds array access happens.
Below that line is the stack trace so we know the calling context.

stack_pointer_return.c is a buggy program with a common error where a function
returns a pointer to a local array.  When the function returns, the local array
is deallocated with the rest of the function frame as it is now out of scope,
thereby leaving the pointer dangling.  Later, that memory location is
reoccupied by a pointer value so that later when the program attempts to send
the bytes in that array, it sends the pointer value instead.  It leads to
nondeterministic behavior likewise:

```
$ ./stack_pointer_return
p = 0x7ffebaff28f8
f8 28 ff ba fe 7f  0  0
$ ./stack_pointer_return
p = 0x7fffd061df08
 8 df 61 d0 ff 7f  0  0
$ ./stack_pointer_return
p = 0x7fff0bc73c08
 8 3c c7  b ff 7f  0  0
```

Let's see if ASAN is able to find this bug:

```
$ ./stack_pointer_return.asan
p = 0x7f4855571060
=================================================================
==55457==ERROR: AddressSanitizer: stack-use-after-return on address 0x7f4855571020 at pc 0x0000004f843d bp 0x7ffdfb807cf0 sp 0x7ffdfb807ce8
READ of size 1 at 0x7f4855571020 thread T0
    #0 0x4f843c in send_data /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_pointer_return.c:7:20
    #1 0x4f875a in main /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_pointer_return.c:31:3
    #2 0x7f4858be53d4 in __libc_start_main (/lib64/libc.so.6+0x223d4)
    #3 0x41b6c9 in _start (/afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_pointer_return.asan+0x41b6c9)

Address 0x7f4855571020 is located in stack of thread T0 at offset 32 in frame
    #0 0x4f85ef in bar /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/stack_pointer_return.c:20
```

Again, stack_pointer_return.c:7:20 is flagged as an illegal read because it is attempting to read a location that has already been deallocated.

## Using Google TSAN (Thread Sanitizer)

If you haven't already, you need to setup some OS environment variables used by Google sanitizer:

```
$ source setup_sanitizer_env.sh
```

datarace.c is a buggy program with a datarace on the variable 'shared'.  Hence,
everytime you run the program you will get nondeterministic output:

```
$ ./datarace
shared=1024461
$ ./datarace
shared=1041862
$ ./datarace
shared=1021775
```

Now let's try using TSAN to discover this bug by running the instrumented binary:

```
$ ./datarace.tsan
==================
WARNING: ThreadSanitizer: data race (pid=56150)
  Write of size 4 at 0x55e6c4959618 by main thread:
    #0 add /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/datarace.c:7:42 (datarace.tsan+0xcb73e)
    #1 main /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/datarace.c:16:3 (datarace.tsan+0xcb7ac)

  Previous write of size 4 at 0x55e6c4959618 by thread T1:
    #0 add /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/datarace.c:7:42 (datarace.tsan+0xcb73e)

  Location is global 'shared' of size 4 at 0x55e6c4959618 (datarace.tsan+0x000000d2e618)

  Thread T1 (tid=56152, running) created by main thread at:
    #0 pthread_create /afs/cs.pitt.edu/usr0/wahn/packages/llvm-project-llvmorg-9.0.1/compiler-rt/lib/tsan/rtl/tsan_interceptors.cc:967 (datarace.tsan+0x286a9)
    #1 main /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/datarace.c:14:3 (datarace.tsan+0xcb79e)

SUMMARY: ThreadSanitizer: data race /afs/pitt.edu/home/w/a/wahn/teaching/cs1632/CS1632_Fall2020/sample_code/nondeterminism/C/datarace.c:7:42 in add
==================
shared=1000000
ThreadSanitizer: reported 1 warnings
```

It tells you exactly what each thread was doing to cause the datarace.  The
"main thread" was executing add in line datarace.c:7:42 and "thread T1" (the
child thread) was likewise executing add at the same source line.  That is
exactly where the unprotected 'shared++' is happening.

## Comparing Google ASAN with Valgrind

You may have used a runtime memory error checking tool called Valgrind in CS
449: Introduction to System Software or somewhere else.  In terms of purpose,
ASAN and Valgrind share common goals.  However, ASAN is superior to Valgrind in
some ways.  That is because ASAN performs instrumentation at the source code
level whereas Valgrind performs instrumentation at the binary level.  A lot of
the semantic information that was present at the source code level is removed
at the binary level, meaning Valgrind instrumentation cannot be as detailed and
as efficient as ASAN instrumentation.  Case in point:

1. ASAN can discover stack buffer overflows where as Valgrind cannot.  The
   compiler is in charge of laying out program variables in memory so it knows
exactly where a variable begins and ends in memory (memory neing stack memory
in the case of local variables).  So it can insert instrumentation to monitor
whether an array access goes beyond the bounds of the array.  At the binary
level (Valgrind), that semantic information is gone.  Valgrind has no way of
knowing the bounds of the array.  Heck, it can't even know whether it is an
array to begin with.  Let's try running stack_overflow on Valgrind to prove our
point:

   ```
   $ valgrind ./stack_overflow
   ...
   ==56869== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)
   ```

   As expected, Valgrind cannot find this error.  Stack overflows are a common
type of defect and security vulnerability to the point that a popular developer
community website is named after it (see https://stackoverflow.com/).  This is
one reason many software QA teams prefer ASAN over Valgrind.

1. ASAN is much faster than Valgrind.  Since the source code provides much more
   semantic information, ASAN can make a much better decision on where
instrumentation is needed.  Also, the instrumentation gets optimized along with
other code using compiler optimizations.  'binary_tree.c' is a benchmark in the
[Language Shootout Benchmark
Suite](https://benchmarksgame-team.pages.debian.net/benchmarksgame/index.html).
Let's time 'binary_tree.c' a) running without instrumentation, b) running with
ASAN instrumentation, and c) running with Valgrind instrumentation:

   ```
   $ time ./binary_tree 15
   ...
   real    0m0.117s
   user    0m0.111s
   sys     0m0.006s

   $ time ./binary_tree.asan 15
   ...
   real    0m0.162s
   user    0m0.147s
   sys     0m0.015s

   $ time valgrind ./binary_tree 15
   ...
   real    0m2.614s
   user    0m2.587s
   sys     0m0.026s
   ```

   'time' is a Linux utility used to time an application.  The last three rows
starting with 'real', 'user', and 'sys' is output from the 'time' utility and
not from the application.  We are going to learn more about it when we talk
about Performance Testing, but for now, all you need to know is that 'real'
measures real time (as in actual wall clock time to run an application).  As
you can see, ASAN results in an approximately 1.4X slowdown whereas Valgrind
results in an approximately 22.3X slowdown!

So, is Valgrind obsolete?  No, Valgrind does have one strong point: that it can
instrument binaries without the need of source code and without the need of
recompilation.  But if you do have the source code (which is typically the case
for tested software), most people would prefer ASAN over Valgrind.

## Submission

There is no submission.  These are just code examples to help your
understanding.  I encourage you to try to debug these errors using the tools we
learned.  If you are not familiar with the Linux environment, you can use
'nano' as a very simple editor.

```
$ nano stack_overflow.c
```

Then, invoke 'make' again to recompile the program:

```
$ make
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w stack_overflow.c -o stack_overflow.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack_overflow.o  -o stack_overflow
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang -c -g -w -fsanitize=address stack_overflow.c -o stack_overflow.asan.o
/afs/cs.pitt.edu/courses/1632/clang-9.0.1/bin/clang stack_overflow.asan.o  -fsanitize=address -o stack_overflow.asan
```

## Resources

* Windows SSH Terminal Client: [Putty](https://www.chiark.greenend.org.uk/~sgtatham/putty/latest.html)
* File Transfer Client: [FileZilla](https://filezilla-project.org/download.php?type=client)
* Linux command line tutorial: [The Linux Command Line](http://linuxcommand.org/lc3_learning_the_shell.php)
